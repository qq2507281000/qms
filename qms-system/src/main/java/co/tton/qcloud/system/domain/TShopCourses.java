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
 * 课程基本信息对象 t_shop_courses
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("课程基本信息对象")
public class TShopCourses extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** 商家Id */
    @Excel(name = "商家Id")
    @ApiModelProperty("商家Id")
    private String shopId;

    /** 课程标题 */
    @Excel(name = "课程标题")
    @ApiModelProperty("课程标题")
    private String title;

    /** 副标题 */
    @Excel(name = "副标题")
    @ApiModelProperty("副标题")
    private String subTitle;

    /** 内容HTML */
    @Excel(name = "内容HTML")
    @ApiModelProperty("内容HTML")
    private String contentHtml;

    /** 是否推荐 */
    @Excel(name = "是否推荐")
    @ApiModelProperty("是否推荐")
    private Integer suggest;

    /** 库存数量 */
    @Excel(name = "库存数量")
    @ApiModelProperty("库存数量")
    private Integer salesCount;

    /** 是否可用 */
    @Excel(name = "是否可用")
    @ApiModelProperty("是否可用")
    private Integer available;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Integer sortKey;

    /** $column.columnComment */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private Integer flag;

    /** SKU保留字段 */
    @Excel(name = "SKU保留字段")
    @ApiModelProperty("SKU保留字段")
    private String sku;

    /** 是否允许使用优惠券 */
    @Excel(name = "是否允许使用优惠券")
    @ApiModelProperty("是否允许使用优惠券")
    private Integer useDiscount;

    /** 商家名称 */
    @Excel(name = "商家名称")
    @ApiModelProperty("商家名称")
    private String shopName;

    @ApiModelProperty("分类Id")
    private String categoryId;

    @ApiModelProperty("运营区域名称")
    private String regionName;

    @ApiModelProperty("运营区域Id")
    private String regionId;

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
            .append("available", getAvailable())
            .append("sortKey", getSortKey())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("sku", getSku())
            .append("useDiscount", getUseDiscount())
            .append("shopName", getShopName())
            .toString();
    }
}
