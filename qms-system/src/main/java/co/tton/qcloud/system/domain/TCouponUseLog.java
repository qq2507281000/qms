package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 【请填写功能名称】对象 t_coupon_use_log
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("优惠卷用户Log对象")
public class TCouponUseLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** $column.columnComment */
    @Excel(name = "优惠券Id")
    @ApiModelProperty("优惠券Id")
    private String couponId;

    /** $column.columnComment */
    @Excel(name = "优惠券编号")
    @ApiModelProperty("优惠券编号")
    private String sno;

    /** $column.columnComment */
    @Excel(name = "会员Id")
    @ApiModelProperty("会员Id")
    private String memberId;

    /** $column.columnComment */
    @Excel(name = "使用时间")
    @ApiModelProperty("使用时间")
    private Date useTime;

    /** $column.columnComment */
    @Excel(name = "面值")
    @ApiModelProperty("面值")
    private Double faceValue;

    /** $column.columnComment */
    @Excel(name = "订单Id")
    @ApiModelProperty("订单Id")
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "订单总金额")
    @ApiModelProperty("订单总金额")
    private Double totalPrice;

    /** $column.columnComment */
    @Excel(name = "实际支付金额")
    @ApiModelProperty("主键")
    private Double realPayPrice;

    /** $column.columnComment */
    @Excel(name = "抵扣金额")
    @ApiModelProperty("抵扣金额")
    private Double discountPrice;

    /** $column.columnComment */
    @Excel(name = "数据状态")
    @ApiModelProperty("数据状态")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("couponId", getCouponId())
            .append("sno", getSno())
            .append("memberId", getMemberId())
            .append("useTime", getUseTime())
            .append("faceValue", getFaceValue())
            .append("orderId", getOrderId())
            .append("totalPrice", getTotalPrice())
            .append("realPayPrice", getRealPayPrice())
            .append("discountPrice", getDiscountPrice())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
