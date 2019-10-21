package co.tton.qcloud.system.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-17 10:09
 */

@Data
public class TOrderModel extends TOrder {

    /***
     * 真实姓名
     */
    private String realName;

    /***
     * 微信昵称
     */
    private String wxName;

    /***
     * 会员注册时间
     */
    private Date regTime;

    /***
     * 会员级别
     */
    private String accountLevel;

    /***
     * 微信头像
     */
    private String avatar;

    /***
     * 会员手机号码
     */
    private String mobile;

    /***
     * 微信OpenId
     */
    private String openId;

    /***
     * 支付流水号
     */
    private String serialNo;

    /***
     * 支付时间
     */
    private Date payTime;

    /***
     * 支付通道
     */
//    private String paymentChannel;

    /***
     * 支付金额
     */
//    private double payPrice;

    /***
     * 支付端返回信息
     */
    private String remoteInfo;

    /***
     * 优惠券Id
     */
    private String couponId;

    /***
     * 优惠券编码
     */
    private String couponNo;

    /***
     * 优惠券面值
     */
    private double faceValue;

    /***
     * 有效期开始时间
     */
    private Date avaBeginTime;

    /**
     * 有小区结束时间
     */
    private Date avaEndTime;

    /***
     * 使用状态
     */
    private String couponUseStatus;

    /***
     * 优惠券领用会员Id
     */
    private String receivedMemberId;

    /***
     * 优惠券领用状态
     */
    private String receivedStatus;

    /***
     * 订单详情
     */
    private List<TOrderDetailModel> details;

    /***
     * 课程时间
     */
    private Date lessionTime;

    /***
     * 小程序订单状态数量
     */
    private String statusCount;

    /***
     * 父级分类
     */
    private String parentId;

    /***
     * 分类名称
     */
    private String categoryName;

    /***
     * 上课时间
     */
    private String coursesTime;

    /***
     * 课程标题
     */
    private String coursesTitle;

    /***
     * 课程图片
     */
    private String coursesImg;
}
