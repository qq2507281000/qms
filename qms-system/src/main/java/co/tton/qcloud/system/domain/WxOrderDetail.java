package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@ApiModel("微信订单详情对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxOrderDetail extends BaseEntity {

    private String id;

    private String babyRealName;

    private String babyNickName;

    private Integer sex;

    private Date birthday;

    private String adultName;

    private String mobile;

    private Double payPrice;

    private Double preRealPrice;

    private Double faceValue;

    private String orderNo;

    private Date bookingTime;

    private String confirmNo;

    private String address;

    private Integer shopHoursBegin;

    private Integer shopHoursEnd;

    private String title;

    private String commentNote;

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
                .toString();
    }
}
