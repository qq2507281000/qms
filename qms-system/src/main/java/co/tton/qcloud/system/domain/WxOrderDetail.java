package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@ApiModel("微信订单详情对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxOrderDetail extends BaseEntity {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("宝宝真实姓名")
    private String babyRealName;

    @ApiModelProperty("宝宝昵称")
    private String babyNickName;

    @ApiModelProperty("宝宝性别")
    private Integer sex;

    @ApiModelProperty("宝宝生日")
    private Date birthday;

    @ApiModelProperty("联系人信息")
    private String adultName;

    @ApiModelProperty("联系电话")
    private String mobile;

    @ApiModelProperty("实收价格")
    private Double payPrice;

    @ApiModelProperty("实际应收价格")
    private Double preRealPrice;

    @ApiModelProperty("优惠券面值")
    private Double faceValue;

    @ApiModelProperty("订单标号")
    private String orderNo;

    @ApiModelProperty("下单时间")
    private Date bookingTime;

    @ApiModelProperty("核销编码")
    private String confirmNo;

    @ApiModelProperty("上课地址")
    private String address;

    @ApiModelProperty("商家营业开始时间")
    private String shopHoursBegin;

    @ApiModelProperty("商家营业结束时间")
    private String shopHoursEnd;

    @ApiModelProperty("课程标题")
    private String title;

    @ApiModelProperty("备注")
    private String commentNote;

    @ApiModelProperty("课程时间")
    private Date lessionTime;

    @ApiModelProperty("上课时间")
    private Date coursesTime;

    @ApiModelProperty("上午_下午")
    private String amPm;

    @ApiModelProperty("联系人")
    private String memberName;

    @ApiModelProperty("课程ID")
    private String coursesId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("babyRealName", getBabyRealName())
                .append("babyNickName", getBabyNickName())
                .append("sex", getSex())
                .append("birthday", getBirthday())
                .append("adultName", getAdultName())
                .append("mobile", getMobile())
                .append("payPrice", getPayPrice())
                .append("preRealPrice", getPreRealPrice())
                .append("faceValue", getFaceValue())
                .append("orderNo", getOrderNo())
                .append("bookingTime", getBookingTime())
                .append("confirmNo", getConfirmNo())
                .append("address", getAddress())
                .append("shopHoursBegin", getShopHoursBegin())
                .append("shopHoursEnd", getShopHoursEnd())
                .append("title", getTitle())
                .append("commentNote", getCommentNote())
                .append("lessionTime", getLessionTime())
                .append("coursesId", getCoursesId())
                .toString();
    }
}
