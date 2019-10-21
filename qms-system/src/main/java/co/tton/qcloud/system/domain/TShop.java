package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 商家信息对象 t_shop
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@ApiModel("商家信息实体类")
@Data
@EqualsAndHashCode(callSuper = false)
public class TShop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("ID")
    private String id;

    /** 商家分类 */
    @ApiModelProperty("商家分类")
    @Excel(name = "商家分类")
    private String categoryId;

    /** 商家名称 */
    @ApiModelProperty("商家名称")
    @Excel(name = "商家名称")
    private String name;

    /** 商家副标题 */
    @ApiModelProperty("商家副标题")
    @Excel(name = "商家副标题")
    private String subject;

    /** 商家描述 */
    @ApiModelProperty("商家描述")
    @Excel(name = "商家描述")
    private String summary;

    /** 区域名称 */
    @ApiModelProperty("区域名称")
    @Excel(name = "区域名称")
    private String regionName;

    /** 封面图片 */
    @ApiModelProperty("封面图片")
    @Excel(name = "封面图片")
    private String coverImg;

    /** 是否推荐 */
    @ApiModelProperty("是否推荐")
    @Excel(name = "是否推荐")
    private Integer suggest;

    /** 排序键值 */
    @ApiModelProperty("排序键值")
    @Excel(name = "排序键值")
    private Integer sortKey;

    /** 排序权重 */
    @ApiModelProperty("排序权重")
    @Excel(name = "排序权重")
    private Long sortWeight;

    /** 地理位置 */
    @ApiModelProperty("地理位置")
    @Excel(name = "地理位置")
    private String address;

    /** 开始营业时间 */
    @ApiModelProperty("开始营业时间")
    @Excel(name = "开始营业时间")
    private String shopHoursBegin;

    /** 结束营业时间 */
    @ApiModelProperty("结束营业时间")
    @Excel(name = "结束营业时间")
    private String shopHoursEnd;

    /** 商家级别 */
    @ApiModelProperty("商家级别")
    @Excel(name = "商家级别")
    private Integer level;

    /** 商家星级 */
    @ApiModelProperty("商家名称")
    @Excel(name = "商家星级")
    private Long stars;

    /** HTML描述 */
    @ApiModelProperty("HTML描述")
    @Excel(name = "HTML描述")
    private String htmlContent;

    /** 商家座机 */
    @ApiModelProperty("商家座机")
    @Excel(name = "商家座机")
    private String telephone;

    /** 商家手机 */
    @ApiModelProperty("商家手机")
    @Excel(name = "商家手机")
    private String mobile;

    /** 联系人姓名 */
    @ApiModelProperty("联系人姓名")
    @Excel(name = "联系人姓名")
    private String contractName;

    /** 微信号 */
    @ApiModelProperty("微信号")
    @Excel(name = "微信号")
    private String wechatNo;

    /** 微信公众平台 */
    @ApiModelProperty("微信公众平台")
    @Excel(name = "微信公众平台")
    private String wechatPlatform;

    /** 个人二维码图片 */
    @ApiModelProperty("个人二维码图片")
    @Excel(name = "个人二维码图片")
    private String wechatNoImg;

    /** 微信公众号图片 */
    @ApiModelProperty("微信公众号图片")
    @Excel(name = "微信公众号图片")
    private String wechatPlatformImg;

    /** 是否显示微信信息 */
    @ApiModelProperty("是否显示微信信息")
    @Excel(name = "是否显示微信信息")
    private Integer wechatShow;

    /** 是否可用 */
    @ApiModelProperty("是否可用")
    @Excel(name = "是否可用")
    private Integer available;

    /** $column.columnComment */
    @ApiModelProperty("数据状态")
    @Excel(name = "数据状态")
    private Integer flag;

    //以下是自定义字段
    @ApiModelProperty("商家月销")
    @Excel(name = "商家月销")
    private Integer shopCount;

    @ApiModelProperty("商家分类名称")
    @Excel(name = "商家分类名称")
    private String  shopCategoryName;

    @ApiModelProperty("关联订单")
    private List<TCategory> tCategoryList;

    @ApiModelProperty("业务区域")
    private String businessRegion;

    @ApiModelProperty("位置信息")
    private String locationRegion;

    @ApiModelProperty("运营区域Id")
    private String regionId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryId", getCategoryId())
            .append("name", getName())
            .append("subject", getSubject())
            .append("summary", getSummary())
            .append("regionName", getRegionName())
            .append("coverImg", getCoverImg())
            .append("suggest", getSuggest())
            .append("sortKey", getSortKey())
            .append("sortWeight", getSortWeight())
            .append("address", getAddress())
            .append("shopHoursBegin", getShopHoursBegin())
            .append("shopHoursEnd", getShopHoursEnd())
            .append("level", getLevel())
            .append("stars", getStars())
            .append("htmlContent", getHtmlContent())
            .append("telephone", getTelephone())
            .append("mobile", getMobile())
            .append("contractName", getContractName())
            .append("wechatNo", getWechatNo())
            .append("wechatPlatform", getWechatPlatform())
            .append("wechatNoImg", getWechatNoImg())
            .append("wechatPlatformImg", getWechatPlatformImg())
            .append("wechatShow", getWechatShow())
            .append("available", getAvailable())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
