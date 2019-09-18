package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课程图片 建议使用minio对象 t_shop_courses_images
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("课程图片信息对象")
public class TShopCoursesImages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** 商家Id */
    @Excel(name = "商家Id")
    @ApiModelProperty("商家Id")
    private String shopId;

    /** 课程Id */
    @Excel(name = "课程Id")
    @ApiModelProperty("课程Id")
    private String coursesId;

    /** 课程图片地址 */
    @Excel(name = "课程图片地址")
    @ApiModelProperty("课程图片地址")
    private String imageUrl;

    /** 排序键值 */
    @Excel(name = "排序键值")
    @ApiModelProperty("排序键值")
    private Integer sortKey;

    /** $column.columnComment */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private Integer flag;

    /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createUser;

    /** $column.columnComment */
    @Excel(name = "修改人")
    @ApiModelProperty("修改人")
    private String updateUser;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shopId", getShopId())
            .append("coursesId", getCoursesId())
            .append("imageUrl", getImageUrl())
            .append("sortKey", getSortKey())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
