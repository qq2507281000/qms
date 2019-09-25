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
 * 分类基础对象 t_category
 *
 * @author qcloud
 * @date 2019-09-25
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("优惠卷用户Log对象Model")
public class TCouponUseLogModel extends BaseEntity {
    @ApiModelProperty("主键")
    private String id;

    @Excel(name = "优惠券Id")
    @ApiModelProperty("优惠券Id")
    private String couponId;

    @Excel(name = "优惠券编号")
    @ApiModelProperty("优惠券编号")
    private String sno;

    @Excel(name = "会员Id")
    @ApiModelProperty("会员Id")
    private String memberId;

    @Excel(name = "使用时间")
    @ApiModelProperty("使用时间")
    private Date useTime;

    @Excel(name = "面值")
    @ApiModelProperty("面值")
    private Double faceValue;

    @Excel(name = "订单Id")
    @ApiModelProperty("订单Id")
    private String orderId;

    @Excel(name = "订单总金额")
    @ApiModelProperty("订单总金额")
    private Double totalPrice;

    @Excel(name = "实际支付金额")
    @ApiModelProperty("主键")
    private Double realPayPrice;

    @Excel(name = "抵扣金额")
    @ApiModelProperty("抵扣金额")
    private Double discountPrice;

    @Excel(name = "数据状态")
    @ApiModelProperty("数据状态")
    private Integer flag;

    @ApiModelProperty("允许使用的店铺等级，-1为所有店铺都可使用。")
    @Excel(name = "允许使用的店铺等级，-1为所有店铺都可使用。")
    private Integer shopLevel;

    @ApiModelProperty("有效结束日期")
    @Excel(name = "有效结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date avaEndTime;

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
            .append("shopLevel", getShopLevel())
            .append("avaEndTime", getAvaEndTime())
            .toString();
    }
}
