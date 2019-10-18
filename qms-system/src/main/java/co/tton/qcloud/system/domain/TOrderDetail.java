package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 【请填写功能名称】对象 t_order_detail
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TOrderDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "订单Id")
    private String orderId;

    /** $column.columnComment */
    @Excel(name = "课程Id")
    private String coursesId;

    /** $column.columnComment */
    @Excel(name = "课程时间Id")
    private String timeId;

    /** $column.columnComment */
    @Excel(name = "价格Id", readConverterExp = "$column.readConverterExp()")
    private String priceId;

    /** $column.columnComment */
    @Excel(name = "应付金额", readConverterExp = "$column.readConverterExp()")
    private Double preRealPrice;

    /** 使用状态（未使用、已使用、已过期） */
    @Excel(name = "使用状态", readConverterExp = "未使用、已使用、已过期")
    private String useStatus;

    /** $column.columnComment */
    @Excel(name = "使用状态", readConverterExp = "$column.readConverterExp()")
    private Integer flag;

    @ApiModelProperty("上课时间：AM&PM")
    private String amPm;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("上课时间")
    private Date coursesTime;

    @ApiModelProperty("子女Id")
    private String childId;

    @ApiModelProperty("子女性别")
    private String childSex;

    @ApiModelProperty("子女姓名")
    private String childName;

    @ApiModelProperty("出生日期")
    private Date childBirthday;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("coursesId", getCoursesId())
            .append("timeId", getTimeId())
            .append("priceId", getPriceId())
            .append("preRealPrice", getPreRealPrice())
            .append("useStatus", getUseStatus())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
