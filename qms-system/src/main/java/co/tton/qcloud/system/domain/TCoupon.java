package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 平台优惠券对象 t_coupon
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 允许使用的分类，为空则所有分类都允许使用 */
    @Excel(name = "允许使用的分类，为空则所有分类都允许使用")
    private String categoryLib;

    /** 优惠券编码 */
    @Excel(name = "优惠券编码")
    private String sno;

    /** 允许使用的店铺等级，-1为所有店铺都可使用。 */
    @Excel(name = "允许使用的店铺等级，-1为所有店铺都可使用。")
    private Integer shopLevel;

    /** 优惠券面值 */
    @Excel(name = "优惠券面值")
    private Double faceValue;

    /** 有效开始日期 */
    @Excel(name = "有效开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date avaBeginTime;

    /** 有效结束日期 */
    @Excel(name = "有效结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date avaEndTime;

    /** 使用状态（已使用，未使用） */
    @Excel(name = "使用状态")
    private String useStatus;

    /** 领取状态（已领取，未领取） */
    @Excel(name = "领取状态")
    private String receiveStatus;

    /** 优惠券自身状态（有效、作废） */
    @Excel(name = "优惠券自身状态")
    private String status;

    /** $column.columnComment */
    @Excel(name = "优惠券自身状态")
    private Integer flag;

    /** 批次编码 */
    @Excel(name = "批次编码")
    private String batchNo;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryLib", getCategoryLib())
            .append("sno", getSno())
            .append("shopLevel", getShopLevel())
            .append("faceValue", getFaceValue())
            .append("avaBeginTime", getAvaBeginTime())
            .append("avaEndTime", getAvaEndTime())
            .append("useStatus", getUseStatus())
            .append("receiveStatus", getReceiveStatus())
            .append("status", getStatus())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("batchNo", getBatchNo())
            .toString();
    }
}
