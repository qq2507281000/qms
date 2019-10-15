package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-15 23:06
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("会员充值对象")
public class TMemberCharging extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("会员Id")
    private String memeberId;

    @ApiModelProperty("充值时间")
    private Date chargingTime;

    @ApiModelProperty("充值金额")
    private Double chargingPrice;

    @ApiModelProperty("会员开始时间")
    private Date beginTime;

    @ApiModelProperty("会员结束时间")
    private Date endTime;

    @ApiModelProperty("Vip等级")
    private int vipLevel;

    @ApiModelProperty("支付平台序列号")
    private String serialNo;

    @ApiModelProperty("数据状态")
    private int flag;

    @ApiModelProperty("支付状态")
    private String payStatus;

    @ApiModelProperty("订单编号")
    private String orderNo;

}
