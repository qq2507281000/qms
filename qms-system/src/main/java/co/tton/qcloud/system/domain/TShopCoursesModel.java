package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序课程详情对象")
public class TShopCoursesModel extends BaseEntity {

    @ApiModelProperty("主键")
    private String id;

    @Excel(name = "商家Id")
    @ApiModelProperty("商家Id")
    private String shopId;

    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    @Excel(name = "副标题")
    @ApiModelProperty("副标题")
    private String subTitle;

    @Excel(name = "内容HTML")
    @ApiModelProperty("内容HTML")
    private String contentHtml;

    @Excel(name = "是否推荐")
    @ApiModelProperty("是否推荐")
    private Integer suggest;

    @Excel(name = "库存数量")
    @ApiModelProperty("库存数量")
    private Integer salesCount;

    @Excel(name = "是否可用")
    @ApiModelProperty("是否可用")
    private Integer available;

    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Integer sortKey;

    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private Integer flag;

    @Excel(name = "是否允许使用优惠券")
    @ApiModelProperty("是否允许使用优惠券")
    private Integer useDiscount;

    @Excel(name = "课程Id")
    @ApiModelProperty("课程Id")
    private String coursesId;

    @Excel(name = "图片地址")
    @ApiModelProperty("图片地址")
    private String imageUrl;

    @Excel(name = "默认基准价格")
    @ApiModelProperty("默认基准价格")
    private Double price;

    @Excel(name = "特殊价格_第一级别")
    @ApiModelProperty("特殊价格_第一级别")
    private Double l1Price;

    @Excel(name = "特殊价格_第二级别")
    @ApiModelProperty("特殊价格_第二级别")
    private Double l2Price;

    @Excel(name = "特殊价格_第三级别")
    @ApiModelProperty("特殊价格_第三级别")
    private Double l3Price;

    @Excel(name = "特殊价格_第思维级别")
    @ApiModelProperty("特殊价格_第四级别")
    private Double l4Price;

    @Excel(name = "特殊价格_第五级别")
    @ApiModelProperty("特殊价格_第五级别")
    private Double l5Price;

    @Excel(name = "特殊价格_第六级别")
    @ApiModelProperty("特殊价格_第六级别")
    private Double l6Price;

    @Excel(name = "特殊价格_第七级别")
    @ApiModelProperty("特殊价格_第七级别")
    private Double l7Price;

    @Excel(name = "特殊价格_第八级别")
    @ApiModelProperty("特殊价格_第八级别")
    private Double l8Price;

    @Excel(name = "特殊价格_第九级别")
    @ApiModelProperty("特殊价格_第九级别")
    private Double l9Price;

    @Excel(name = "特殊价格级别，默认为0，没有特殊价格。")
    @ApiModelProperty("特殊价格级别，默认为0，没有特殊价格。")
    private Integer specialPriceLevel;

    @Excel(name = "限制每人购买数量，-1为不限制。")
    @ApiModelProperty("限制每人购买数量，-1为不限制。")
    private Integer perLimitBuy;

    @Excel(name = "sku")
    @ApiModelProperty("sku")
    private String sku;

    @Excel(name = "分类名称")
    @ApiModelProperty("分类名称")
    private String name;

    @Excel(name = "课程基本信息表-分类Id")
    @ApiModelProperty("课程基本信息表-分类Id")
    private String categoryId;

    @Excel(name = "月销订单数")
    @ApiModelProperty("月销订单数")
    private String count;

    @Excel(name = "课程图片")
    @ApiModelProperty("课程图片")
    private String[] images;

    @Excel(name = "商家名称")
    @ApiModelProperty("商家名称")
    private String shopName;

    @Excel(name = "商家地址")
    @ApiModelProperty("商家地址")
    private String address;

    @Excel(name = "课程描述")
    @ApiModelProperty("课程描述")
    private String summary;

    @Excel(name = "商家手机")
    @ApiModelProperty("商家手机")
    private String telephone;

    @Excel(name = "商家座机")
    @ApiModelProperty("商家座机")
    private String mobile;

    @Excel(name = "联系人姓名")
    @ApiModelProperty("联系人姓名")
    private String contractName;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
                .append("name", getName())
                .append("categoryId", getCategoryId())
                .append("count", getCount())
                .append("images", getImages())
                .append("shopName", getImages())
                .append("address", getImages())
                .append("summary", getImages())
                .append("telephone", getImages())
                .append("mobile", getImages())
                .append("contractName", getImages())
                .toString();
    }
}
