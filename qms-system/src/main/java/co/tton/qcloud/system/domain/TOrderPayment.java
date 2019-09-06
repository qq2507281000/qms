package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 t_order_payment
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TOrderPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "订单Id", readConverterExp = "$column.readConverterExp()")
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "支付通道", readConverterExp = "$column.readConverterExp()")
    private String paymentChannel;

    /** $column.columnComment */
    @Excel(name = "流水号", readConverterExp = "$column.readConverterExp()")
    private String serialNo;

    /** $column.columnComment */
    @Excel(name = "远端支付信息", readConverterExp = "$column.readConverterExp()")
    private String remoteInfo;

    /** $column.columnComment */
    @Excel(name = "数据状态", readConverterExp = "$column.readConverterExp()")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("paymentChannel", getPaymentChannel())
            .append("serialNo", getSerialNo())
            .append("remoteInfo", getRemoteInfo())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
