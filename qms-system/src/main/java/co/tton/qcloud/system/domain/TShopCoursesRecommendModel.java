package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 推荐课程 t_shop_courses_recommend
 * 
 * @author suiwb
 * @date 2019-11-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TShopCoursesRecommendModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** $column.columnComment */
    @Excel(name = "课程id")
    @ApiModelProperty("课程id")
    private String coursesId;

    /** $column.columnComment */
    @Excel(name = "课程名称")
    @ApiModelProperty("课程名称")
    private String title;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Integer sortKey;

    @ApiModelProperty("运营区域Id")
    private String regionId;

    /** $column.columnComment */
    @ApiModelProperty("数据状态")
    @Excel(name = "数据状态")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("coursesId", getCoursesId())
            .append("sortKey", getSortKey())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
