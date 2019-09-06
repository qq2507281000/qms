package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 订单使用状况对象 t_order_use_log
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TOrderUseLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "订单Id", readConverterExp = "$column.readConverterExp()")
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "课程Id", readConverterExp = "$column.readConverterExp()")
    private String coursesId;

    /** $column.columnComment */
    @Excel(name = "使用时间", readConverterExp = "$column.readConverterExp()")
    private Date useTime;

    /** $column.columnComment */
    @Excel(name = "会员Id", readConverterExp = "$column.readConverterExp()")
    private String memberId;

    /** $column.columnComment */
    @Excel(name = "宝宝Id", readConverterExp = "$column.readConverterExp()")
    private String childId;

    /** $column.columnComment */
    @Excel(name = "店铺Id", readConverterExp = "$column.readConverterExp()")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "数据状态", readConverterExp = "$column.readConverterExp()")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("coursesId", getCoursesId())
            .append("useTime", getUseTime())
            .append("memberId", getMemberId())
            .append("childId", getChildId())
            .append("shopId", getShopId())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
