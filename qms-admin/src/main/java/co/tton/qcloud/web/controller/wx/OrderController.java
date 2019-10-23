package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.IpUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.model.OrderEvaluationModel;
import co.tton.qcloud.system.model.OrderModel;
import co.tton.qcloud.system.model.OrderPayModel;
import co.tton.qcloud.system.model.OrderResponseModel;
import co.tton.qcloud.system.service.*;
import co.tton.qcloud.system.wxservice.ITOrderUseEvaluationService;
import co.tton.qcloud.web.controller.common.CommonController;
import co.tton.qcloud.web.minio.MinioFileService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static co.tton.qcloud.common.utils.DateUtils.parseDate;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:49
 */

@RestController
@RequestMapping("/api/v1.0/order")
@Api(tags = "小程序订单")
public class OrderController extends BaseController {

    @Autowired
    private ITOrderService tOrderService;
    @Autowired
    private ITOrderUseEvaluationService tOrderUseEvaluationService;
    @Autowired
    private WxPayService wxService;
    @Autowired
    private ITOrderService orderService;
    @Autowired
    private ITOrderCouponService tOrderCouponService;
    @Autowired
    private ITCouponService tCouponService;

    @ApiOperation("订单支付回调接口")
    @RequestMapping(value = "/pay/notify",method = RequestMethod.POST)
    public AjaxResult parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        try{
            final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);
            String orderNo = notifyResult.getOutTradeNo();
            String transactionId = notifyResult.getTransactionId();
            String payTime = notifyResult.getTimeEnd();
            Date date = DateUtil.parse(payTime,"yyyyMMddHHmmss");
            TOrder order = orderService.selectTOrderByNo(orderNo);
            if(order != null) {
                order.setPayStatus("PAID");
                order.setUseStatus("UNUSED");
                order.setBillStatus("EXECUTING");
                order.setVerifyStatus("UNCONFIRM");
                order.setSerialNo(transactionId);
                order.setPayTime(date);
                order.setUpdateBy(order.getMemberId());
                order.setUpdateTime(DateUtils.getNowDate());
                int result = orderService.updateTOrder(order);
                if(result == 1){
                    TOrderCoupon tOrderCoupon = new TOrderCoupon();
                    tOrderCoupon.setOrderId(order.getId());
                    tOrderCoupon.setFlag(0);
                    List<TOrderCoupon> tOrderCoupons=tOrderCouponService.selectTOrderCouponList(tOrderCoupon);

                    TCoupon tCoupon = new TCoupon();
                    tCoupon.setId(tOrderCoupons.get(0).getCouponId());
                    tCoupon.setFlag(1);
                    tCoupon.setUpdateTime(DateUtils.getNowDate());
                    tCoupon.setUpdateBy(order.getMemberId());
                    tCoupon.setUseStatus("USE");
                    int count=tCouponService.updateTCoupon(tCoupon);
                    if(count == 1){
                        return AjaxResult.success("支付回调成功。");
                    }else{
                        return AjaxResult.error("优惠卷销毁失败。");
                    }
                }
                else{
                    return AjaxResult.error("支付回调失败。");
                }
            }
            else{
                return AjaxResult.error("未能找到订单信息。");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("订单支付回调接口异常。");
            return error("订单支付回调接口异常。\r\n" + ex.getMessage());
        }
    }

    //    @RequiresPermissions("wx:order:submit")
    @ApiOperation("提交订单")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public AjaxResult submitOrder(@RequestBody OrderModel model){
        try{
            if(model == null){
                return AjaxResult.error("参数不允许为空。");
            }
            else{
                OrderResponseModel responseModel = tOrderService.submitOrder(model);
                if(StringUtils.equals(responseModel.getStatus(),"SUCCESS")){
                    WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
                    request.setVersion("1.0");
                    request.setDeviceInfo("000000000000");
                    request.setBody(responseModel.getOrder().getSubject());
                    request.setAttach("课程订单");
                    request.setOutTradeNo(responseModel.getOrder().getOrderNo());
                    request.setTotalFee((int)(responseModel.getOrder().getPayPrice() * 100));
                    request.setSpbillCreateIp(IpUtils.getHostIp());
                    request.setTimeStart(DateUtils.dateTimeNow());
                    request.setNotifyUrl(Global.getOrderPayNotifyUrl());
                    request.setTradeType("JSAPI");
                    request.setOpenid(model.getOpenId());
                    WxPayMpOrderResult orderResult = wxService.createOrder(request);
                    return AjaxResult.success("订单创建成功，支付中...", orderResult);
                }
                else{
                    return AjaxResult.error("订单创建失败["+responseModel.getStatus()+"]。\r\n" + responseModel.getMessage());
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("提交订单时发生异常。",ex);
            return AjaxResult.error("提交订单时发生异常。",ex);
        }
    }

    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    @ApiOperation("订单支付")
    public AjaxResult orderPay(@RequestParam("orderId") String orderId, @RequestParam("openId") String openId){
        try{
            if(StringUtils.isEmpty(orderId)){
                return AjaxResult.error("参数错误，orderId不允许为空。");
            }
            else{
                TOrder order = orderService.selectTOrderById(orderId);
                if(order == null){
                    return AjaxResult.error("未能找到订单信息。");
                }
                else{
                    if(StringUtils.equalsAnyIgnoreCase(order.getPayStatus(),"UNPAY")){
                        //订单状态为未支付。
                        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
                        request.setVersion("1.0");
                        request.setDeviceInfo("000000000000");
                        request.setBody(order.getSubject());
                        request.setAttach("课程订单");
                        request.setOutTradeNo(order.getOrderNo());
                        request.setTotalFee((int)(order.getPayPrice() * 100));
                        request.setSpbillCreateIp(IpUtils.getHostIp());
                        request.setTimeStart(DateUtils.dateTimeNow());
                        request.setNotifyUrl(Global.getOrderPayNotifyUrl());
                        request.setTradeType("JSAPI");
                        request.setOpenid(openId);
                        WxPayMpOrderResult orderResult = wxService.createOrder(request);
                        return AjaxResult.success("订单支付中...",orderResult);
                    }
                    else{
                        return AjaxResult.error("当前订单["+order.getOrderNo()+"]已经支付。");
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("订单支付时发生异常。", ex);
            return AjaxResult.error("订单支付时发生异常。");
        }
    }

//    @PostMapping("/pay")
//    @ApiOperation("订单支付")
//    public AjaxResult orderPay(OrderPayModel model){
//        try{
//            if(model == null){
//                return AjaxResult.error("订单支付参数不允许为空。");
//            }
//            else{
//                WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
//                request.setVersion("1.0");
//                request.setDeviceInfo("000000000000");
//                request.setBody(model.getSubject());
//                request.setAttach("课程订单");
//                request.setOutTradeNo(model.getOrderNo());
//                request.setTotalFee((int)(model.getPrice()*100));
//                request.setSpbillCreateIp(IpUtils.getHostIp());
//                request.setTimeStart(DateUtils.dateTimeNow());
//                request.setNotifyUrl(Global.getOrderPayNotifyUrl());
//                request.setTradeType("JSAPI");
//                request.setOpenid(model.getOpenId());
//                wxService.createOrder(request);
//                return AjaxResult.success("支付中...");
//            }
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//            logger.error("订单支付时发生异常。",ex);
//            return AjaxResult.error("订单支付时发生异常。");
//        }
//    }

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:order:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    @ApiOperation("获取所有订单列表,根据状态获取订单列表")
    public AjaxResult<List<TOrderModel>> getOrderList(@RequestParam(value = "memberid")String memberId,
                                                      @RequestParam(value = "billstatus",required = false)String billStatus,
                                                      @RequestParam(value = "paystatus",required = false)String payStatus,
                                                      @RequestParam(value = "usestatus",required = false)String useStatus){
        if(StringUtils.isNotEmpty(memberId)){
            TOrderModel tOrderNew = new TOrderModel();
            tOrderNew.setMemberId(memberId);
            if(StringUtils.isNotEmpty(billStatus)){
                tOrderNew.setBillStatus(billStatus);
            }
            if(StringUtils.isNotEmpty(payStatus)){
                tOrderNew.setPayStatus(payStatus);
            }
            if(StringUtils.isNotEmpty(useStatus)){
                tOrderNew.setUseStatus(useStatus);
            }
            List<TOrderModel> tOrders =tOrderService.getOrderList(tOrderNew);
            if(StringUtils.isNotEmpty(tOrders)){
                return AjaxResult.success("获取成功",tOrders);
            }else{
                return AjaxResult.success("会员无订单",tOrders);
            }
        }else{
            return AjaxResult.error("参数错误");
        }
    }

    /***
     *
     * @param orderId
     * @return
     */
//    @RequiresPermissions("wx:order:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation("获取订单详情")
    public AjaxResult<WxOrderDetail> getOrderDetail(@PathVariable("id")String orderId){
        if (StringUtils.isNotEmpty(orderId)) {
            WxOrderDetail wxOrderDetail = tOrderService.getOrderDetail(orderId);
            if (StringUtils.isNull(wxOrderDetail)){
                return AjaxResult.success("无效订单号");
            }else {
                return AjaxResult.success("获取订单详情成功。", wxOrderDetail);
            }
        }else {
            return AjaxResult.error("报错：orderId为空。");
        }
    }

    //    @RequiresPermissions("wx:evaluation:insert")
    @RequestMapping(value = "/evaluation",method = RequestMethod.POST)
    @ApiOperation("订单评价")
    public AjaxResult insertOrderUseEvaluation(@RequestBody TOrderUseEvaluation tOrder) throws IOException {
        if(StringUtils.isNotNull(tOrder)){
            tOrder.setFlag(Constants.DATA_NORMAL);
            Date str = parseDate(DateUtils.getDate());
            tOrder.setCreateTime(str);
            String id = StringUtils.genericId();
            tOrder.setId(id);
            String [] strArray = tOrder.getImageArray();
            if(StringUtils.isNotEmpty(strArray)){
                //解析图片数组成字符串
                StringBuilder imageurl = new StringBuilder();
                for(int i = 0;i<strArray.length;i++){
                    if(i!=0){
                        imageurl.append(',');
                    }
                    imageurl.append(strArray[i]);
                }
                tOrder.setImageUrl(imageurl.toString());
            }
            if(StringUtils.isNotEmpty(tOrder.getStar())){
                tOrder.setStar(tOrder.getStar());
            }else{
                tOrder.setStar("0");
            }
            int number = tOrderUseEvaluationService.insertOrderUseEvaluation(tOrder);
            if(number == 1){
                return AjaxResult.success("数据保存成功。",number);
            }else{
                return AjaxResult.success("数据保存失败。",number);
            }
        }else{
            return AjaxResult.error("参数错误,或会员ID为空");
        }
    }
}