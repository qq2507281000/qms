package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会员基本信息对象 t_member
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序会员对象")
public class TMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** $column.columnComment */
    @ApiModelProperty("会员微信Id")
    @Excel(name = "会员微信Id")
    private String openId;

    /** $column.columnComment */
    @ApiModelProperty("微信名")
    @Excel(name = "微信名")
    private String wxName;

    /** $column.columnComment */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String mobile;

    /** $column.columnComment */
    @Excel(name = "注册时间")
    @ApiModelProperty("注册时间")
    private Date regTime;

    /** $column.columnComment */
    @Excel(name = "星级")
    @ApiModelProperty("星级")
    private Double star;

    /** 账户状态（可用，锁定） */
    @Excel(name = "账户状态")
    @ApiModelProperty("账户状态")
    private String status;

    /** $column.columnComment */
    @Excel(name = "数据状态", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("数据状态")
    private Integer flag;

    /** $column.columnComment */
    @Excel(name = "真实姓名", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("真实姓名")
    private String realName;

    /** 账户级别 */
    @Excel(name = "账户级别")
    @ApiModelProperty("账户级别")
    private String accountLevel;

    @ApiModelProperty("VIP开始时间")
    private Date vipBeginTime;

    @ApiModelProperty("VIP结束时间")
    private Date vipEndTime;

    /** 积分 */
    @Excel(name = "积分")
    @ApiModelProperty("积分")
    private Integer score;

    @ApiModelProperty("关联会员用户子女信息表")
    private List<TMemberBaby> TMemberBabyList;

    @Excel(name = "微信头像")
    @ApiModelProperty("微信头像")
    private String img;

    @Excel(name = "")
    @ApiModelProperty("")
    private String avatar;

    @ApiModelProperty("用户Token")
    private Serializable token;

    @ApiModelProperty("微信端返回的JSON信息")
    private String wxJson;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("运营商")
    private String operator;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
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
            .append("img", getImg())
            .append("avatar", getAvatar())
            .append("wxJson", getWxJson())
            .append("city", getCity())
            .append("operator", getOperator())
            .toString();
    }
}
