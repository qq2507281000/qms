package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课程基本信息对象 t_shop_courses
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TShopCourses extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 商家Id */
    @Excel(name = "商家Id")
    private String shopId;

    /** 课程标题 */
    @Excel(name = "课程标题")
    private String title;

    /** 副标题 */
    @Excel(name = "副标题")
    private String subTitle;

    /** 内容HTML */
    @Excel(name = "内容HTML")
    private String contentHtml;

    /** 是否推荐 */
    @Excel(name = "是否推荐")
    private Integer suggest;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Integer salesCount;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private Integer avaiable;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortKey;

    /** $column.columnComment */
    @Excel(name = "排序")
    private Integer flag;

    /** SKU保留字段 */
    @Excel(name = "SKU保留字段")
    private String sku;

    /** 是否允许使用优惠券 */
    @Excel(name = "是否允许使用优惠券")
    private Integer useDiscount;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shopId", getShopId())
            .append("title", getTitle())
            .append("subTitle", getSubTitle())
            .append("contentHtml", getContentHtml())
            .append("suggest", getSuggest())
            .append("salesCount", getSalesCount())
            .append("avaiable", getAvaiable())
            .append("sortKey", getSortKey())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("sku", getSku())
            .append("useDiscount", getUseDiscount())
            .toString();
    }
}
