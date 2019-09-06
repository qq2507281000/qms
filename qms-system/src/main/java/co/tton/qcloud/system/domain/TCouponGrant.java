package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 优惠券生成参数库对象 t_coupon_grant
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TCouponGrant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "分类库")
    private String categoryLib;

    /** $column.columnComment */
    @Excel(name = "店铺级别")
    private Integer shopLevel;

    /** $column.columnComment */
    @Excel(name = "批次编号")
    private String batchNo;

    /** 面值模式（固定面值，随机面值） */
    @Excel(name = "面值模式")
    private String faceValueMode;

    /** 数量 */
    @Excel(name = "数量")
    private Integer amount;

    /** 总金额 */
    @Excel(name = "总金额")
    private Double totalPrice;

    /** $column.columnComment */
    @Excel(name = "总金额", width = 30, dateFormat = "yyyy-MM-dd")
    private Date avaBeginTime;

    /** $column.columnComment */
    @Excel(name = "总金额", width = 30, dateFormat = "yyyy-MM-dd")
    private Date avaEndTime;

    /** 是否生成 */
    @Excel(name = "是否生成")
    private Integer isBuild;

    /** $column.columnComment */
    @Excel(name = "是否生成")
    private Integer flag;

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryLib", getCategoryLib())
            .append("shopLevel", getShopLevel())
            .append("batchNo", getBatchNo())
            .append("faceValueMode", getFaceValueMode())
            .append("amount", getAmount())
            .append("totalPrice", getTotalPrice())
            .append("avaBeginTime", getAvaBeginTime())
            .append("avaEndTime", getAvaEndTime())
            .append("isBuild", getIsBuild())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
