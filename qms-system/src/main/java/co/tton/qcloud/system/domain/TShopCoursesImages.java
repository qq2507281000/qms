package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
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
public class TShopCoursesImages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 商家Id */
    @Excel(name = "商家Id")
    private String shopId;

    /** 课程Id */
    @Excel(name = "课程Id")
    private String coursesId;

    /** 课程图片地址 */
    @Excel(name = "课程图片地址")
    private String imageUrl;

    /** 排序键值 */
    @Excel(name = "排序键值")
    private Integer sortKey;

    /** $column.columnComment */
    @Excel(name = "排序键值")
    private Integer flag;

    /** $column.columnComment */
    @Excel(name = "排序键值")
    private String createUser;

    /** $column.columnComment */
    @Excel(name = "排序键值")
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
