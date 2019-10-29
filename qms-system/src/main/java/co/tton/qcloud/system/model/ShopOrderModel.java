package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: swb
 * @description:
 * @author: suiwenbai@TTON
 * @create: 2019-10-29 15:51
 */

@Data
@ApiModel("订单核销")
public class ShopOrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单ID")
    private String id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("联系电话")
    private String mobile;

    @ApiModelProperty("订单主题")
    private String subject;

    @ApiModelProperty("课程标题")
    private String coursesTitle;

    @ApiModelProperty("上课时间")
    private Date lessionTime;

    @ApiModelProperty("上午下午")
    private String ampm;

    //支付状态、未支付(UNPAY)、已支付(PAID)
    @ApiModelProperty("支付状态")
    private String payStatus;

    //使用状态(未使用、已使用、已过期）
    @ApiModelProperty("使用状态")
    private String useStatus;

    @ApiModelProperty("实际应收价格")
    private double preRealPrice;

    @ApiModelProperty("实收价格")
    private double payPrice;

    //核销状态（未核销、部分核销、已核销）
    @ApiModelProperty("核销状态")
    private String verifyStatus;

    //订单状态（下单中、进行中、评价中、已完成）
    @ApiModelProperty("订单状态")
    private String billStatus;

}
