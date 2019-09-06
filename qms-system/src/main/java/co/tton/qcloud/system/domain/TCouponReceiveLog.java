package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 【请填写功能名称】对象 t_coupon_receive_log
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TCouponReceiveLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "优惠券Id")
    private String couponId;

    /** $column.columnComment */
    @Excel(name = "优惠券编号")
    private String sno;

    /** $column.columnComment */
    @Excel(name = "会员Id")
    private String memberId;

    /** $column.columnComment */
    @Excel(name = "领取时间")
    private Date receivedTime;

    /** $column.columnComment */
    @Excel(name = "面值")
    private String faceValue;

    /** $column.columnComment */
    @Excel(name = "优惠券开始时间")
    private Date avaBeginTime;

    /** $column.columnComment */
    @Excel(name = "优惠券结束时间")
    private Date avaEndTime;

    /** $column.columnComment */
    @Excel(name = "数据状态")
    private Integer flag;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("couponId", getCouponId())
            .append("sno", getSno())
            .append("memberId", getMemberId())
            .append("receivedTime", getReceivedTime())
            .append("faceValue", getFaceValue())
            .append("avaBeginTime", getAvaBeginTime())
            .append("avaEndTime", getAvaEndTime())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
