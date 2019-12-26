package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 推荐商家 t_shop_recommend
 * 
 * @author suiwb
 * @date 2019-11-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TShopRecommendModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** $column.columnComment */
    @Excel(name = "商家id")
    @ApiModelProperty("商家id")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "商家名称")
    @ApiModelProperty("商家名称")
    private String shopName;

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
            .append("shopId", getShopId())
            .append("sortKey", getSortKey())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
