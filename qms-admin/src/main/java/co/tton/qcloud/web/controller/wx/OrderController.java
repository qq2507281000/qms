package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.date.DateUtil;
import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.IpUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.model.OrderModel;
import co.tton.qcloud.system.model.OrderResponseModel;
import co.tton.qcloud.system.service.ITCouponService;
import co.tton.qcloud.system.service.ITOrderCouponService;
import co.tton.qcloud.system.service.ITOrderService;
import co.tton.qcloud.system.wxservice.ITOrderUseEvaluationService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
  @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
  public AjaxResult parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
    try {
      final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);
      String orderNo = notifyResult.getOutTradeNo();
      String transactionId = notifyResult.getTransactionId();
      String payTime = notifyResult.getTimeEnd();
      Date date = DateUtil.parse(payTime, "yyyyMMddHHmmss");
      TOrder order = orderService.selectTOrderByNo(orderNo);
      if (order != null) {
        order.setPayStatus("PAID");
        order.setUseStatus("UNUSED");
        order.setBillStatus("EXECUTING");
        order.setVerifyStatus("UNCONFIRM");
        order.setSerialNo(transactionId);
        order.setPayTime(date);
        order.setUpdateBy(order.getMemberId());
        order.setUpdateTime(DateUtils.getNowDate());
        int result = orderService.updateTOrder(order);
        if (result == 1) {
          int count = setCouponStatus(order);//优惠卷逻辑删除
          if(count == 0) {
            return AjaxResult.error("销毁失败。");
          }
          return AjaxResult.success(" ，支付回调成功。");
        } else {
          return AjaxResult.error("支付回调失败。");
        }
      } else {
        return AjaxResult.error("未能找到订单信息。");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("订单支付回调接口异常。");
      return error("订单支付回调接口异常。\r\n" + ex.getMessage());
    }
  }

  //    @RequiresPermissions("wx:order:submit")
  @ApiOperation("提交订单")
  @RequestMapping(value = "/", method = RequestMethod.POST)
  public AjaxResult submitOrder(@RequestBody OrderModel model) {
    try {
      if (model == null) {
        return AjaxResult.error("参数不允许为空。");
      } else {
        OrderResponseModel responseModel = tOrderService.submitOrder(model);
        if (StringUtils.equals(responseModel.getStatus(), "SUCCESS")) {
          WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
          request.setVersion("1.0");
          request.setDeviceInfo("000000000000");
          request.setBody(responseModel.getOrder().getSubject());
          request.setAttach("课程订单");
          request.setOutTradeNo(responseModel.getOrder().getOrderNo());
          request.setTotalFee((int) (responseModel.getOrder().getPayPrice() * 100));
          request.setSpbillCreateIp(IpUtils.getHostIp());
          request.setTimeStart(DateUtils.dateTimeNow());
          request.setNotifyUrl(Global.getOrderPayNotifyUrl());
          request.setTradeType("JSAPI");
          request.setOpenid(model.getOpenId());
          WxPayMpOrderResult orderResult = wxService.createOrder(request);
          return AjaxResult.success("订单创建成功，支付中...", orderResult);
        } else {
          return AjaxResult.error("订单创建失败[" + responseModel.getStatus() + "]。\r\n" + responseModel.getMessage());
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("提交订单时发生异常。", ex);
      return AjaxResult.error("提交订单时发生异常。", ex);
    }
  }

  @RequestMapping(value = "/pay", method = RequestMethod.POST)
  @ApiOperation("订单支付")
  public AjaxResult orderPay(@RequestParam("orderId") String orderId, @RequestParam("openId") String openId) {
    try {
      if (StringUtils.isEmpty(orderId)) {
        return AjaxResult.error("参数错误，orderId不允许为空。");
      } else {
        TOrder order = orderService.selectTOrderById(orderId);
        if (order == null) {
          return AjaxResult.error("未能找到订单信息。");
        } else {
          if (StringUtils.equalsAnyIgnoreCase(order.getPayStatus(), "UNPAY")) {
            //订单状态为未支付。
            WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
            request.setVersion("1.0");
            request.setDeviceInfo("000000000000");
            request.setBody(order.getSubject());
            request.setAttach("课程订单");
            request.setOutTradeNo(order.getOrderNo());
            request.setTotalFee((int) (order.getPayPrice() * 100));
            request.setSpbillCreateIp(IpUtils.getHostIp());
            request.setTimeStart(DateUtils.dateTimeNow());
            request.setNotifyUrl(Global.getOrderPayNotifyUrl());
            request.setTradeType("JSAPI");
            request.setOpenid(openId);
            WxPayMpOrderResult orderResult = wxService.createOrder(request);
            return AjaxResult.success("订单支付中...", orderResult);
          } else {
            return AjaxResult.error("当前订单[" + order.getOrderNo() + "]已经支付。");
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("订单支付时发生异常。", ex);
      return AjaxResult.error("订单支付时发生异常。");
    }
  }

  //优惠卷销毁
  private int setCouponStatus(TOrder order) {
    TOrderCoupon tOrderCoupon = new TOrderCoupon();
    tOrderCoupon.setOrderId(order.getId());
    tOrderCoupon.setFlag(0);
    //查询为了获取优惠卷ID
    List<TOrderCoupon> tOrderCoupons = tOrderCouponService.selectTOrderCouponList(tOrderCoupon);

    TCoupon tCoupon = new TCoupon();
    tCoupon.setId(tOrderCoupons.get(0).getCouponId());
    tCoupon.setFlag(1);
    tCoupon.setUpdateTime(DateUtils.getNowDate());
    tCoupon.setUpdateBy(order.getMemberId());
    tCoupon.setUseStatus("USE");
    int count = tCouponService.updateTCoupon(tCoupon);//优惠卷逻辑删除
    return count;
  }

  /***
   *
   * @param
   * @return
   */
//    @RequiresPermissions("wx:order:list")
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ApiOperation("获取所有订单列表,根据状态获取订单列表")
  public AjaxResult<List<TOrderModel>> getOrderList(@RequestParam(value = "memberid") String memberId,
                                                    @RequestParam(value = "billstatus", required = false) String billStatus,
                                                    @RequestParam(value = "paystatus", required = false) String payStatus,
                                                    @RequestParam(value = "usestatus", required = false) String useStatus) {
    if (StringUtils.isNotEmpty(memberId)) {
      TOrderModel tOrderNew = new TOrderModel();
      tOrderNew.setMemberId(memberId);
      if (StringUtils.isNotEmpty(billStatus)) {
        tOrderNew.setBillStatus(billStatus);
      }
      if (StringUtils.isNotEmpty(payStatus)) {
        tOrderNew.setPayStatus(payStatus);
      }
      if (StringUtils.isNotEmpty(useStatus)) {
        tOrderNew.setUseStatus(useStatus);
      }
      //条件memberId是获取所有订单列表，其他状态获取订单列表
      List<TOrderModel> tOrders = tOrderService.getOrderList(tOrderNew);
      if (StringUtils.isNotEmpty(tOrders)) {
        return AjaxResult.success("获取成功", tOrders);
      } else {
        return AjaxResult.error("会员无订单");
      }
    } else {
      return AjaxResult.error("参数错误");
    }
  }

  /***
   *
   * @param orderId
   * @return
   */
//    @RequiresPermissions("wx:order:detail")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation("获取订单详情")
  public AjaxResult<WxOrderDetail> getOrderDetail(@PathVariable("id") String orderId) {
    if (StringUtils.isNotEmpty(orderId)) {
      WxOrderDetail wxOrderDetail = tOrderService.getOrderDetail(orderId);//查询订单详情
      if (StringUtils.isNull(wxOrderDetail)) {
        return AjaxResult.error("无效订单号");
      } else {
        return AjaxResult.success("获取订单详情成功。", wxOrderDetail);
      }
    } else {
      return AjaxResult.error("参数错误。");
    }
  }

  //    @RequiresPermissions("wx:evaluation:insert")
  @RequestMapping(value = "/evaluation", method = RequestMethod.POST)
  @ApiOperation("订单评价")
  public AjaxResult insertOrderUseEvaluation(@RequestBody TOrderUseEvaluation tOrder) throws IOException {
    if (StringUtils.isNotNull(tOrder)) {
      tOrder.setFlag(Constants.DATA_NORMAL);
      Date str = parseDate(DateUtils.getDate());
      tOrder.setCreateTime(str);
      String id = StringUtils.genericId();
      tOrder.setId(id);
      String[] strArray = tOrder.getImageArray();
      if (StringUtils.isNotEmpty(strArray)) {
        //解析图片数组成字符串
        StringBuilder imageurl = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
          if (i != 0) {
            imageurl.append(',');
          }
          imageurl.append(strArray[i]);
        }
        tOrder.setImageUrl(imageurl.toString());
      }
      if (StringUtils.isNotEmpty(tOrder.getStar())) {
        tOrder.setStar(tOrder.getStar());
      } else {
        tOrder.setStar("0");
      }
      int number = tOrderUseEvaluationService.insertOrderUseEvaluation(tOrder);//插入订单评价
      if (number == 1) {
        return AjaxResult.success("数据保存成功。", number);
      } else {
        return AjaxResult.success("数据保存失败。", number);
      }
    } else {
      return AjaxResult.error("参数错误,或会员ID为空");
    }
  }
}