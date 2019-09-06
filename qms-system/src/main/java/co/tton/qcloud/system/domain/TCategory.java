package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分类基础对象 t_category
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 父级分类 */
    @Excel(name = "父级分类")
    private String parentId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String name;

    /** 排序键值 */
    @Excel(name = "排序键值")
    private Integer sortKey;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private Integer available;

    /** $column.columnComment */
    @Excel(name = "数据状态")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("name", getName())
            .append("sortKey", getSortKey())
            .append("icon", getIcon())
            .append("available", getAvailable())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
