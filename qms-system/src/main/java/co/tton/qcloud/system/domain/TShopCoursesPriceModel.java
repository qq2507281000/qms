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
public class TShopCoursesPriceModel extends BaseEntity
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

    /** $column.columnComment */
    @ApiModelProperty("价格Id")
    private String priceId;

    /** 默认基准价格 */
    @Excel(name = "默认基准价格")
    @ApiModelProperty("默认基准价格")
    private Double price;

    /** 特殊价格_第一级别 */
    @Excel(name = "特殊价格_第一级别")
    @ApiModelProperty("特殊价格_第一级别")
    private Double l1Price;

    /** 特殊价格_第二级别（保留） */
    @Excel(name = "特殊价格_第二级别", readConverterExp = "保留")
    @ApiModelProperty("特殊价格_第二级别")
    private Double l2Price;

    /** $column.columnComment */
    @Excel(name = "特殊价格_第三级别", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("特殊价格_第三级别")
    private Double l3Price;

    /** $column.columnComment */
    @Excel(name = "特殊价格_第四级别", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("特殊价格_第四级别")
    private Double l4Price;

    /** $column.columnComment */
    @Excel(name = "优惠价格", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("优惠价格")
    private Double l5Price;

    /** $column.columnComment */
    @Excel(name = "特殊价格_第六级别", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("特殊价格_第六级别")
    private Double l6Price;

    /** $column.columnComment */
    @Excel(name = "特殊价格_第七级别", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("特殊价格_第七级别")
    private Double l7Price;

    /** $column.columnComment */
    @Excel(name = "特殊价格_第八级别", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("特殊价格_第八级别")
    private Double l8Price;

    /** $column.columnComment */
    @Excel(name = "特殊价格_第九级别", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("特殊价格_第九级别")
    private Double l9Price;

    /** 特殊价格级别，默认为0，没有特殊价格。 */
    @Excel(name = "特殊价格级别，默认为0，没有特殊价格。")
    @ApiModelProperty("特殊价格级别，默认为0，没有特殊价格。")
    private Integer specialPriceLevel;

    /** 一级标题 */
    @Excel(name = "一级标题")
    @ApiModelProperty("一级标题")
    private String subTitleOne;

    /** 二级标题 */
    @Excel(name = "二级标题")
    @ApiModelProperty("二级标题")
    private String subTitleTwo;

    /** 三级标题 */
    @Excel(name = "补充标题")
    @ApiModelProperty("补充标题")
    private String subTitleThree;

    /** 四级标题 */
    @Excel(name = "四级标题")
    @ApiModelProperty("四级标题")
    private String subTitleFour;

    /** 限制每人购买数量，-1为不限制。 */
    @Excel(name = "限制每人购买数量，-1为不限制。")
    @ApiModelProperty("限制每人购买数量，-1为不限制。")
    private Integer perLimitBuy;

}
