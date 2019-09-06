package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 专题信息对象 t_topic
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TTopic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 专题名称 */
    @Excel(name = "专题名称")
    private String name;

    /** 专题图标 */
    @Excel(name = "专题图标")
    private String icon;

    /** 排序键值 */
    @Excel(name = "排序键值")
    private Integer sortKey;

    /** 是否在首页显示 */
    @Excel(name = "是否在首页显示")
    private Integer showInMain;

    /** 专题所属分类 */
    @Excel(name = "专题所属分类")
    private String categoryId;

    /** 专题描述 */
    @Excel(name = "专题描述")
    private String summary;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private Integer available;

    /** 是否推荐 */
    @Excel(name = "是否推荐")
    private Integer suggest;

    /** $column.columnComment */
    @Excel(name = "是否推荐")
    private Long flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("icon", getIcon())
            .append("sortKey", getSortKey())
            .append("showInMain", getShowInMain())
            .append("categoryId", getCategoryId())
            .append("summary", getSummary())
            .append("available", getAvailable())
            .append("suggest", getSuggest())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
