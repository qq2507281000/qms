package co.tton.qcloud.system.service.impl;

import java.util.Date;
import java.util.List;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.mapper.*;
import co.tton.qcloud.system.model.OrderModel;
import co.tton.qcloud.system.model.OrderResponseModel;
import co.tton.qcloud.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sun.swing.StringUIClientPropertyKey;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Slf4j
@Service
public class TOrderServiceImpl implements ITOrderService 
{
    @Autowired
    private TOrderMapper tOrderMapper;

    @Autowired
    private ITOrderDetailService detailService;

    @Autowired
    private ITShopCoursesService coursesService;

    @Autowired
    private ITShopCoursesPriceService coursesPriceService;

    @Autowired
    private ITCouponService couponService;

    @Autowired
    private ITMemberService memberService;

    @Autowired
    private ITShopService shopService;

    @Autowired
    private ITOrderCouponService orderCouponService;

    @Autowired
    private ITCouponUseLogService couponUseLogService;

    @Autowired
    private ITOrderDetailService orderDetailService;

    @Autowired
    private TransactionTemplate transactionTemplate;


    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TOrder selectTOrderById(String id)
    {
        return tOrderMapper.selectTOrderById(id);
    }

    @Override
    public TOrder selectTOrderByNo(String no) {
        return tOrderMapper.selectTOrderByNo(no);
    }


    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrder 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TOrder> selectTOrderList(TOrder tOrder)
    {
        return tOrderMapper.selectTOrderList(tOrder);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrder 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTOrder(TOrder tOrder)
    {
        tOrder.setCreateTime(DateUtils.getNowDate());
        return tOrderMapper.insertTOrder(tOrder);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrder 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTOrder(TOrder tOrder)
    {
        tOrder.setUpdateTime(DateUtils.getNowDate());
        return tOrderMapper.updateTOrder(tOrder);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOrderByIds(String ids)
    {
        return tOrderMapper.deleteTOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderById(String id)
    {
        return tOrderMapper.deleteTOrderById(id);
    }

    /**
     * 查询所有订单列表
     *
     * @param
     * @return 结果
     */
    @Override
    public List<TOrderModel> getOrderList(TOrderModel tOrderModel) {
        return tOrderMapper.getOrderList(tOrderModel);
    }

    /**
     * 微信小程序 查询所有订单详情
     *
     * @param orderId
     * @return 结果
     */
    @Override
    public WxOrderDetail getOrderDetail(String orderId) {
        return tOrderMapper.getOrderDetail(orderId);
    }

    /**
     * 微信小程序 根据订单状态获取订单数量
     *
     * @param tOrder
     * @return 结果
     */
    @Override
    public TOrderModel getCountOrder(TOrderModel tOrder) {
        return tOrderMapper.getCountOrder(tOrder);
    }

    /**
     * 微信小程序 课程下单时选择的类别
     *
     * @param tOrder
     * @return 结果
     */
    @Override
    public List<TOrderModel> getBillStatusCourses(TOrder tOrder) {
        return tOrderMapper.getBillStatusCourses(tOrder);
    }


    @Override
    public OrderResponseModel submitOrder(OrderModel orderModel) {
//        final OrderResponseModel model = new OrderResponseModel();
//        try{
//            model.setStatus("SAVING");
//            model.setMessage("正在保存订单...");
//
//            OrderResponseModel respModel = transactionTemplate.execute(new TransactionCallback<OrderResponseModel>() {
//                @Override
//                public OrderResponseModel doInTransaction(TransactionStatus transactionStatus) {
//                    return saveOrder(orderModel);
//                }
//            });
//
//            model.setStatus(respModel.getStatus());
//            model.setMessage(respModel.getMessage());
//
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//            log.error("提交订单发生异常。",ex);
//            model.setStatus("EXCEPTION");
//            model.setMessage(ex.getMessage());
//
//        }
//        return model;
        return saveOrder(orderModel);
    }

    /***
     * 保存订单
     * @param orderModel
     * @return
     */
    public OrderResponseModel saveOrder(OrderModel orderModel) {
        OrderResponseModel model = new OrderResponseModel();
        try{
            model.setStatus("SAVING");
            model.setMessage("正在保存订单...");
            String openId = orderModel.getOpenId();
            String memberId = orderModel.getMemeberId();
            String shopId = orderModel.getShopId();
            String coursesId = orderModel.getCoursesId();
            String priceId = orderModel.getPriceId();

            if(StringUtils.isEmpty(openId)){
                model.setStatus("ARGUMENT ERROR");
                model.setMessage("参数错误，openId不允许为空。");
                return model;
            }
            if(StringUtils.isEmpty(memberId)){
                model.setStatus("ARGUMENT ERROR");
                model.setMessage("参数错误，memberId不允许为空。");
                return model;
            }
            if(StringUtils.isEmpty(shopId)){
                model.setStatus("ARGUMENT ERROR");
                model.setMessage("参数错误，shopId不允许为空。");
                return model;
            }
            if(StringUtils.isEmpty(coursesId)){
                model.setStatus("ARGUMENT ERROR");
                model.setMessage("参数错误，coursesId不允许为空。");
                return model;
            }
            if(StringUtils.isEmpty(priceId)){
                model.setStatus("ARGUMENT ERROR");
                model.setMessage("参数错误，priceId不允许为空。");
                return model;
            }
//            TMember member = memberService.selectTMemberById(memberId);
            TShop shop = shopService.selectTShopById(shopId);
            TShopCourses courses = coursesService.selectTShopCoursesById(coursesId);
            TShopCoursesPrice coursesPrice = coursesPriceService.selectTShopCoursesPriceById(priceId);
            TCoupon coupon = null;

            double price = orderModel.getOrderPrice();
            TOrder order = new TOrder();
            String orderId = StringUtils.genericId();
            String orderNo = StringUtils.genericOrderNo();
            order.setId(orderId);
            order.setOrderNo(orderNo);
            order.setSubject("互动派课程订单-" + courses.getTitle());
            order.setAddress(shop.getAddress());
            order.setShopId(shopId);
            order.setShopName(shop.getName());
            order.setPreRealPrice(price);
            order.setPayPrice(price);
            order.setMemberName(orderModel.getContractUser());
            order.setMobile(orderModel.getContractMobile());
            order.setPaymentChannel("wechatpay");
            if(StringUtils.isNotEmpty(orderModel.getCouponId())){
                order.setHaveDiscount(1);
                coupon = couponService.selectTCouponById(orderModel.getCouponId());
            }
            else{
                order.setHaveDiscount(0);
            }
            order.setBookingTime(new Date());
            order.setPayStatus("UNPAY");
            order.setBillStatus("BOOKING");
            order.setMemberId(memberId);
            order.setFlag(Constants.DATA_NORMAL);
            order.setCreateBy(memberId);
            order.setCreateTime(DateUtils.getNowDate());
            tOrderMapper.insertTOrder(order);

            if(coupon != null){
                TOrderCoupon orderCoupon = new TOrderCoupon();
                orderCoupon.setId(StringUtils.genericId());
                orderCoupon.setOrderId(orderId);
                orderCoupon.setCouponId(orderModel.getCouponId());
                orderCoupon.setFaceValue(coupon.getFaceValue());
                orderCoupon.setDeductionValue(coupon.getFaceValue());
                orderCoupon.setUseTime(DateUtils.getNowDate());
                orderCoupon.setFlag(Constants.DATA_NORMAL);
                orderCoupon.setCreateBy(memberId);
                orderCoupon.setCreateTime(DateUtils.getNowDate());
                orderCouponService.insertTOrderCoupon(orderCoupon);

                TCouponUseLog couponUseLog = new TCouponUseLog();
                couponUseLog.setId(StringUtils.genericId());
                couponUseLog.setCouponId(orderModel.getCouponId());
                couponUseLog.setMemberId(memberId);
                couponUseLog.setUseTime(DateUtils.getNowDate());
                couponUseLog.setFaceValue(coupon.getFaceValue());
                couponUseLog.setOrderId(orderId);
                couponUseLog.setTotalPrice(orderModel.getOrderPrice());
                couponUseLog.setRealPayPrice(orderModel.getOrderPrice());
                couponUseLog.setDiscountPrice(coupon.getFaceValue());
                couponUseLog.setFlag(Constants.DATA_NORMAL);
                couponUseLog.setCreateTime(DateUtils.getNowDate());
                couponUseLog.setCreateBy(memberId);
                couponUseLogService.insertTCouponUseLog(couponUseLog);
            }
            TOrderDetail orderDetail = new TOrderDetail();
            orderDetail.setId(StringUtils.genericId());
            orderDetail.setOrderId(orderId);
            orderDetail.setCoursesId(coursesId);
            orderDetail.setPriceId(priceId);
            orderDetail.setCoursesTime(orderModel.getCoursesTime());
            orderDetail.setAmPm(orderModel.getAmPm());
            orderDetail.setPreRealPrice(coursesPrice.getPrice());
            orderDetail.setRemark(orderModel.getRemark());
            orderDetail.setFlag(Constants.DATA_NORMAL);
            orderDetail.setCreateBy(memberId);
            orderDetail.setCreateTime(DateUtils.getNowDate());
            orderDetail.setUseStatus("UNUSE");
            orderDetail.setConfirmNo(StringUtils.randomCode());
            orderDetail.setChildId(orderModel.getBabyId());
            orderDetail.setChildSex(orderModel.getBabySex());
            orderDetail.setChildName(orderModel.getBabyName());
            orderDetail.setChildBirthday(orderModel.getBabyBirthday());
            orderDetailService.insertTOrderDetail(orderDetail);

            model.setStatus("SUCCESS");
            model.setOrder(order);
            model.setMessage("订单保存成功，支付中...");

        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("保存订单时发生异常。",ex);
            model.setStatus("EXCEPTION");
            model.setMessage(ex.getMessage());
        }
        return model;
    }


    /***
     * 获取完整订单信息
     * @param id 订单Id
     * @return
     */
    @Override
    public TOrderModel selectFullOrderById(String id) {
        TOrderModel orderModel = tOrderMapper.selectFullOrderById(id);
        List<TOrderDetailModel> details = detailService.selectTOrderDetailModelList(id);
        orderModel.setDetails(details);
        return orderModel;
    }
}
