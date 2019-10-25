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

@ApiModel("订单评价实体对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class TOrderUseEvaluation extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /** $column.columnComment */
    @Excel(name = "会员Id")
    @ApiModelProperty(value="会员Id")
    private String memberId;

    /** $column.columnComment */
    @Excel(name = "评价图片地址")
    @ApiModelProperty(value="评价图片地址")
    private String imageUrl;

    /** $column.columnComment */
    @Excel(name = "评价内容")
    @ApiModelProperty(value="评价内容")
    private String evaluation;

    /** $column.columnComment */
    @Excel(name = "评价星级")
    @ApiModelProperty(value="评价星级")
    private String star;

    /** $column.columnComment */
    @Excel(name = "课程id")
    @ApiModelProperty(value="课程id")
    private String coursesId;

    @Excel(name = "数据状态")
    @ApiModelProperty(value="数据状态")
    private Integer flag;

    @ApiModelProperty(value="评价图片地址集合")
    private List<String> imageUrls;

    @ApiModelProperty(value="评价图片地址数组")
    private String[] imageArray;

    @ApiModelProperty(value="微信")
    private String  wxName;

    @ApiModelProperty(value="微信头像")
    private String  wxImg;

    @ApiModelProperty(value="评价会员Id")
    private String memberid;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("imageUrl", getImageUrl())
            .append("imageUrls", getImageUrls())
            .append("evaluation", getEvaluation())
            .append("star", getStar())
            .append("coursesId", getCoursesId())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("memberId", getMemberId())
            .append("memberid", getMemberId())
            .append("wxName", getWxName())
            .append("wxImg", getWxImg())
            .toString();
    }
}
