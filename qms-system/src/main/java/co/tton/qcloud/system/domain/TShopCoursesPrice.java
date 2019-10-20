package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课程价格对象 t_shop_courses_price
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TShopCoursesPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** 商家id */
    @Excel(name = "商家id")
    @ApiModelProperty("商家id")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "课程id")
    @ApiModelProperty("课程id")
    private String coursesId;

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

    /** $column.columnComment */
    @Excel(name = "数据状态")
    @ApiModelProperty("数据状态")
    private Integer flag;

    /** $column.columnComment */
    @Excel(name = "商家名称")
    @ApiModelProperty("商家名称")
    private String shopName;

    /** $column.columnComment */
    @Excel(name = "课程名称")
    @ApiModelProperty("课程名称")
    private String title;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shopId", getShopId())
            .append("coursesId", getCoursesId())
            .append("price", getPrice())
            .append("l1Price", getL1Price())
            .append("l2Price", getL2Price())
            .append("l3Price", getL3Price())
            .append("l4Price", getL4Price())
            .append("l5Price", getL5Price())
            .append("l6Price", getL6Price())
            .append("l7Price", getL7Price())
            .append("l8Price", getL8Price())
            .append("l9Price", getL9Price())
            .append("specialPriceLevel", getSpecialPriceLevel())
            .append("subTitleOne", getSubTitleOne())
            .append("subTitleTwo", getSubTitleTwo())
            .append("subTitleThree", getSubTitleThree())
            .append("subTitleFour", getSubTitleFour())
            .append("perLimitBuy", getPerLimitBuy())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("shopName", getShopName())
            .append("title", getTitle())
            .toString();
    }
}
