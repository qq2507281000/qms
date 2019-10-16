package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 会员基本信息对象 Model
 *
 * @author qcloud
 * @date 2019-09-25
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序会员对象")
public class TMemberModel extends BaseEntity {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("会员微信Id")
    @Excel(name = "会员微信Id")
    private String openId;

    @ApiModelProperty("微信名")
    @Excel(name = "微信名")
    private String wxName;

    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String mobile;

    @Excel(name = "注册时间")
    @ApiModelProperty("注册时间")
    private Date regTime;

    @Excel(name = "星级")
    @ApiModelProperty("星级")
    private Double star;

    @Excel(name = "账户状态")
    @ApiModelProperty("账户状态")
    private String status;

    @Excel(name = "数据状态", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("数据状态")
    private Integer flag;

    @Excel(name = "真实姓名", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("真实姓名")
    private String realName;

    @Excel(name = "账户级别")
    @ApiModelProperty("账户级别")
    private String accountLevel;

    @Excel(name = "积分")
    @ApiModelProperty("积分")
    private Integer score;

    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /** 支付状态、未支付(UNPAY)、已支付(PAID) */
    @Excel(name = "支付状态、未支付(UNPAY)、已支付(PAID)")
    @ApiModelProperty(value = "支付状态",allowableValues = "UNPAY,PAID",notes = "UNPAY(未支付),PAID(已支付)")
    private String payStatus;

    /** 使用状态(未使用、已使用、已过期） */
    @Excel(name = "使用状态(未使用、已使用、已过期）")
    @ApiModelProperty(value = "使用状态",allowableValues = "UNUSED,USED,EXPIRED",notes = "UNUSED(未使用),USED(已使用),EXPIRED(已过期)")
    private String useStatus;

    /** 订单状态（下单中、进行中、评价中、已完成） */
    @Excel(name = "订单状态", readConverterExp = "下单中、进行中、评价中、已完成")
    @ApiModelProperty(value="订单状态", allowableValues = "BOOKING,EXECUTING,EVALUATING,FINISHED",notes = "BOOKING(下单中),EXECUTING(执行中),EVALUATING(评价中),FINISHED(已完成)")
    private String billStatus;

    @ApiModelProperty("VIP开始时间")
    private Date vipBeginTime;

    @ApiModelProperty("VIP结束时间")
    private Date vipEndTime;

  @Override
    public String toString() {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
          .append("id", getId())
          .append("openId", getOpenId())
          .append("wxName", getWxName())
          .append("mobile", getMobile())
          .append("regTime", getRegTime())
          .append("star", getStar())
          .append("status", getStatus())
          .append("flag", getFlag())
          .append("realName", getRealName())
          .append("accountLevel", getAccountLevel())
          .append("score", getScore())
          .append("createTime", getCreateTime())
          .append("createBy", getCreateBy())
          .append("updateTime", getUpdateTime())
          .append("updateBy", getUpdateBy())
          .append("orderNo", getOrderNo())
          .append("payStatus", getPayStatus())
          .append("useStatus", getUseStatus())
          .append("billStatus", getBillStatus())
          .toString();
    }


}
