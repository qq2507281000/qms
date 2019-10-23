package co.tton.qcloud.system.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-14 21:51
 */

@Data
@ApiModel("订单对象")
public class OrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 微信端用户OpenId
     */
    @ApiModelProperty("微信端用户OpenId")
    private String openId;

    @ApiModelProperty("会员Id")
    private String memeberId;

    @ApiModelProperty("店铺Id")
    private String shopId;

    @ApiModelProperty("课程Id")
    private String coursesId;

    @ApiModelProperty("优惠券Id")
    private String couponId;

    @ApiModelProperty("价格Id")
    private String priceId;

    @ApiModelProperty("联系人姓名")
    private String contractUser;

    @ApiModelProperty("联系人电话")
    private String contractMobile;

    @ApiModelProperty(value = "上课时间")
    private Date coursesTime;

    @ApiModelProperty(value="上课时间，上午&下午",allowableValues = "AM,PM")
    private String amPm;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("宝宝Id")
    private String babyId;

    @ApiModelProperty("宝宝姓名")
    private String babyName;

    @ApiModelProperty("宝宝性别")
    private String babySex;

    @ApiModelProperty("宝宝生日")
    private Date babyBirthday;

    @ApiModelProperty("订单实际应付金额")
    private double orderPrice;

    @ApiModelProperty("备注")
    private String commentNote;
}
