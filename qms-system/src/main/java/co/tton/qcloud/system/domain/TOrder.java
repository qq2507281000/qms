package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 【请填写功能名称】对象 t_order
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 订单标题 */
    @Excel(name = "订单标题")
    private String subject;

    /** 授课地址 */
    @Excel(name = "授课地址")
    private String address;

    /** $column.columnComment */
    @Excel(name = "授课地址")
    private String shopId;

    /** 实际应收价格 */
    @Excel(name = "实际应收价格")
    private Double preRealPrice;

    /** 实收价格 */
    @Excel(name = "实收价格")
    private Double payPrice;

    /** 支付通道 */
    @Excel(name = "支付通道")
    private String paymentChannel;

    /** 支付通道流水单号 */
    @Excel(name = "支付通道流水单号")
    private String serialNo;

    /** 是否使用抵值券 */
    @Excel(name = "是否使用抵值券")
    private Integer haveDiscount;

    /** 下单时间 */
    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bookingTime;

    /** 支付时间 */
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 支付状态、未支付(UNPAY)、已支付(PAID) */
    @Excel(name = "支付状态、未支付(UNPAY)、已支付(PAID)")
    private String payStatus;

    /** 使用状态(未使用、已使用、已过期） */
    @Excel(name = "使用状态(未使用、已使用、已过期）")
    private String useStatus;

    /** 订单状态（下单中、进行中、评价中、已完成） */
    @Excel(name = "订单状态", readConverterExp = "下=单中、进行中、评价中、已完成")
    private String billStatus;

    /** 核销状态（未核销、部分核销、已核销） */
    @Excel(name = "核销状态", readConverterExp = "未=核销、部分核销、已核销")
    private String verifyStatus;

    /** $column.columnComment */
    @Excel(name = "数据状态", readConverterExp = "$column.readConverterExp()")
    private Integer flag;

    /** $column.columnComment */
    @Excel(name = "会员Id", readConverterExp = "$column.readConverterExp()")
    private String memberId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("subject", getSubject())
            .append("address", getAddress())
            .append("shopId", getShopId())
            .append("preRealPrice", getPreRealPrice())
            .append("payPrice", getPayPrice())
            .append("paymentChannel", getPaymentChannel())
            .append("serialNo", getSerialNo())
            .append("haveDiscount", getHaveDiscount())
            .append("bookingTime", getBookingTime())
            .append("payTime", getPayTime())
            .append("payStatus", getPayStatus())
            .append("useStatus", getUseStatus())
            .append("billStatus", getBillStatus())
            .append("verifyStatus", getVerifyStatus())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("memberId", getMemberId())
            .toString();
    }
}
