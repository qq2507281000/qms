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
import java.time.LocalDate;
import java.time.LocalTime;
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
    private ITOrderUseEvaluationService tOrderUseEvaluationService;
    @Autowired
    private WxPayService wxService;

    //    @RequiresPermissions("wx:order:submit")
    @ApiOperation("提交订单")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public AjaxResult submitOrder(OrderModel model){
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
                    request.setNotifyUrl(Global.getNotifyUrl());
                    request.setTradeType("JSAPI");
                    request.setOpenid(model.getOpenId());
                    wxService.createOrder(request);
                    return AjaxResult.success("订单创建成功，支付中...",responseModel);
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
            return AjaxResult.error("参数错误,或会员ID为空");
        }
    }

}
