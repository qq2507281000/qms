package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-15 23:29
 */

@Data
public class MemberChargingModel {

    @ApiModelProperty("会员openId")
    private String openId;

    @ApiModelProperty("会员Id")
    private String memeberId;

    @ApiModelProperty("支付金额")
    private double price;

}
