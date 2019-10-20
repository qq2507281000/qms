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
 * 首页滚动广告对象 t_banner
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@ApiModel("首页滚动广告实体类")
@Data
@EqualsAndHashCode(callSuper = false)
public class TBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("id")
    private String id;

    /** 图片地址 */
    @ApiModelProperty("图片地址")
    @Excel(name = "图片地址")
    private String img;

    /** 跳转URL */
    @ApiModelProperty("跳转URL")
    @Excel(name = "跳转URL")
    private String url;

    /** 跳转类型 */
    @ApiModelProperty("跳转类型")
    @Excel(name = "跳转类型")
    private String targetType;

    /** 是否可用 */
    @ApiModelProperty("是否可用")
    @Excel(name = "是否可用")
    private Integer available;

    /** 排序键值 */
    @ApiModelProperty("排序键值")
    @Excel(name = "排序键值")
    private Integer sortKey;

    /** $column.columnComment */
    @ApiModelProperty("排序键值")
    @Excel(name = "排序键值")
    private Integer flag;

    @ApiModelProperty("城市Id")
    private String cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("img", getImg())
            .append("url", getUrl())
            .append("targetType", getTargetType())
            .append("available", getAvailable())
            .append("sortKey", getSortKey())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
