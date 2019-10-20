package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类基础对象 t_category
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("课程分类信息对象")
public class TCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** 父级分类 */
    @Excel(name = "父级分类")
    @ApiModelProperty("父级分类")
    private String parentId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    @ApiModelProperty("分类名称")
    private String name;

    /** 排序键值 */
    @Excel(name = "排序键值")
    @ApiModelProperty("排序键值")
    private Integer sortKey;

    /** 图标 */
    @Excel(name = "图标")
    @ApiModelProperty("图标")
    private String icon;

    /** 是否可用 */
    @Excel(name = "是否可用")
    @ApiModelProperty("是否可用")
    private Integer available;

    /** $column.columnComment */
    @Excel(name = "数据状态")
    @ApiModelProperty("数据状态")
    private Integer flag;

    @Excel(name = "父级分类名称")
    @ApiModelProperty("父级分类名称")
    private String parentName;

    @Excel(name = "子级分类数据")
    @ApiModelProperty("子级分类数据")
    private List<TCategory> children = new ArrayList<>();

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
            .append("parentName", getParentName())
            .append("childTCategoryList", getChildren())
            .toString();
    }
}
