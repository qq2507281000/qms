package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 t_shop_courses_time
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TShopCoursesTime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 商家Id */
    @Excel(name = "商家Id")
    private String shopId;

    /** 课程id */
    @Excel(name = "课程id")
    private String coursesId;

    /** 星期（1~7） */
    @Excel(name = "星期", readConverterExp = "1~7")
    private Integer wkNo;

    /** 上午下午(0,1) */
    @Excel(name = "上午下午(0,1)")
    private Integer amFm;

    /** 开始时间(0700) */
    @Excel(name = "开始时间(0700)")
    private String bTime;

    /** 结束时间(1200) */
    @Excel(name = "结束时间(1200)")
    private String eTime;

    /** $column.columnComment */
    @Excel(name = "结束时间(1200)")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shopId", getShopId())
            .append("coursesId", getCoursesId())
            .append("wkNo", getWkNo())
            .append("amFm", getAmFm())
            .append("bTime", getBTime())
            .append("eTime", getETime())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
