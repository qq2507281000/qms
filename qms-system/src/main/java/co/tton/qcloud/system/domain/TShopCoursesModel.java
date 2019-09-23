package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序课程详情对象")
public class TShopCoursesModel extends BaseEntity {

    private String id;

    private String shopId;

    private String title;

    private String subTitle;

    private String contentHtml;

    private Integer suggest;

    private Integer salesCount;

    private Integer avaiable;

    private Integer sortKey;

    private Integer flag;

    private Integer useDiscount;

    private String coursesId;

    private String imageUrl;

    private Double price;

    private Double l1Price;

    private Double l2Price;

    private Double l3Price;

    private Double l4Price;

    private Double l5Price;

    private Double l6Price;

    private Double l7Price;

    private Double l8Price;

    private Double l9Price;

    private Integer specialPriceLevel;

    private Integer perLimitBuy;

    private String sku;

    private String name;

    private String categoryId;

    private String count;

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
                .append("avaiable", getAvaiable())
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
                .toString();
    }
}
