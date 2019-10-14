package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-14 22:59
 */

@ApiModel("订单支付信息")
@Data
public class OrderPayModel {

    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单标题")
    private String subject;

    @ApiModelProperty("微信端用户OpenId")
    private String openId;

    @ApiModelProperty("支付金额")
    private float price;

}
