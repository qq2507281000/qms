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
import co.tton.qcloud.system.service.ITOrderService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.system.service.ITShopService;
import co.tton.qcloud.system.wxservice.ITOrderUseEvaluationService;
import co.tton.qcloud.web.controller.common.CommonController;
import co.tton.qcloud.web.minio.MinioFileService;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    private MinioFileService minioFileService;
    @Autowired
    private ITOrderUseEvaluationService tOrderUseEvaluationService;
    @Autowired
    private ITShopService shopService;
    @Autowired
    private ITShopCoursesService shopCoursesService;
    @Autowired
    private WxPayService wxService;

    //    @RequiresPermissions("wx:order:submit")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public AjaxResult submitOrder(OrderModel model){
        try{
            if(model == null){
                return AjaxResult.error("参数不允许为空。");
            }
            else{

                String coursesName = "";
                String shopAddress = "";
                String shopName = "";
//                String serialNo = "";

                TShop shop = shopService.selectTShopById(model.getShopId());
                shopName = shop.getName();
                shopAddress = shop.getAddress();

                TShopCourses courses = shopCoursesService.selectTShopCoursesById(model.getCoursesId());
                coursesName = courses.getTitle();

                String orderNo = StringUtils.genericOrderNo();
                TOrder order = new TOrder();
                String id = StringUtils.genericId();
                order.setId(id);
                order.setOrderNo(orderNo);
                order.setSubject("互动派课程订单-" + coursesName);
                order.setAddress(shopAddress);
                order.setShopId(model.getShopId());
                order.setShopName(shopName);
                order.setPreRealPrice(model.getOrderPrice());
                order.setPayPrice(model.getOrderPrice());
                order.setPaymentChannel("wechatpay");
                if(StringUtils.isNotEmpty(model.getCouponId())){
                    order.setHaveDiscount(1);
                }
                else{
                    order.setHaveDiscount(0);
                }
//                order.setSerialNo(serialNo);
                order.setBookingTime(new Date());
                order.setPayStatus("UNPAY");
                order.setBillStatus("BOOKING");
                order.setMemberId(model.getMemeberId());
                order.setFlag(Constants.DATA_NORMAL);
                int result = tOrderService.insertTOrder(order);
                if(result == 1){
                    AjaxResult ar = AjaxResult.success("订单提交成功。");
                    ar.put("orderId",id);
                    ar.put("orderNo",orderNo);
                    ar.put("subject",order.getSubject());
                    ar.put("price",model.getOrderPrice());
                    return ar;
                }
                else{
                    return AjaxResult.error("订单提交失败。");
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("提交订单时发生异常。",ex);
            return AjaxResult.error("提交订单时发生异常。");
        }
    }

    @PostMapping("/pay")
    @ApiOperation("订单支付")
    public AjaxResult orderPay(OrderPayModel model){
        try{
            if(model == null){
                return AjaxResult.error("订单支付参数不允许为空。");
            }
            else{
                WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
                request.setVersion("1.0");
                request.setDeviceInfo("000000000000");
                request.setBody(model.getSubject());
                request.setAttach("课程订单");
                request.setOutTradeNo(model.getOrderNo());
                request.setTotalFee((int)(model.getPrice()*100));
                request.setSpbillCreateIp(IpUtils.getHostIp());
                request.setTimeStart(DateUtils.dateTimeNow());
                request.setNotifyUrl(Global.getNotifyUrl());
                request.setTradeType("JSAPI");
                request.setOpenid(model.getOpenId());
                wxService.createOrder(request);
                return AjaxResult.success("支付中...");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("订单支付时发生异常。",ex);
            return AjaxResult.error("订单支付时发生异常。");
        }
    }

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:order:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    @ApiOperation("获取所有订单列表,根据状态获取订单列表")
    public AjaxResult<List<TOrder>> getOrderList(@RequestParam(value = "memberid")String memberId,
                                                 @RequestParam(value = "billstatus",required = false)String billStatus,
                                                 @RequestParam(value = "paystatus",required = false)String payStatus,
                                                 @RequestParam(value = "usestatus",required = false)String useStatus){
        if(StringUtils.isNotEmpty(memberId)){
            TOrder tOrderNew = new TOrder();
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
            List<TOrder> tOrders =tOrderService.getOrderList(tOrderNew);
            return AjaxResult.success("获取分类成功。",tOrders);
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
    @ApiOperation("订单和课程评价")
    public AjaxResult insertOrderUseEvaluation(@RequestParam(value = "orderno",required = false)String orderNo,
                                               @RequestParam(value = "memberid",required = false)String memberId,
                                               @RequestParam(value = "imageurl",required = false)String[] imageurls,
                                               @RequestParam(value = "evaluation",required = false)String evaluation,
                                               @RequestParam(value = "coursesid",required = false)String coursesId,
                                               @RequestParam(value = "star",required = false)String star) throws IOException {
        if(StringUtils.isNotEmpty(memberId)){
            TOrderUseEvaluation tOrder = new TOrderUseEvaluation();
            tOrder.setMemberId(memberId);
            tOrder.setFlag(Constants.DATA_NORMAL);
            tOrder.setCreateTime(DateUtils.getNowDate());
            String id = StringUtils.genericId();
            tOrder.setId(id);
            if(StringUtils.isNotEmpty(imageurls)){
                StringBuilder imageurl = new StringBuilder();
                for(int i = 0;i<imageurls.length;i++){
                    if(i!=0){
                        imageurl.append(',');
                    }
                    imageurl.append(imageurls[i]);
                }
                tOrder.setImageUrl(imageurl.toString());
            }
            if(StringUtils.isNotEmpty(orderNo)){
                tOrder.setOrderNo(orderNo);
            }
            if(StringUtils.isNotEmpty(evaluation)){
                tOrder.setEvaluation(evaluation);
            }
            if(StringUtils.isNotEmpty(coursesId)){
                tOrder.setCoursesId(coursesId);
            }
            if(StringUtils.isNotEmpty(star)){
                tOrder.setStar(star);
            }else{
                tOrder.setStar("0");
            }
            int number = tOrderUseEvaluationService.insertOrderUseEvaluation(tOrder);
            if(number>0){
                return AjaxResult.success("数据保存成功。",number);
            }else{
                return AjaxResult.success("数据保存失败。");
            }
        }else{
            return AjaxResult.error("参数错误");
        }
    }

}
