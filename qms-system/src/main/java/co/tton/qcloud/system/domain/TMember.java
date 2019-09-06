package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 会员基本信息对象 t_member
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "会员微信Id")
    private String openId;

    /** $column.columnComment */
    @Excel(name = "微信名")
    private String wxName;

    /** $column.columnComment */
    @Excel(name = "手机号码")
    private String mobile;

    /** $column.columnComment */
    @Excel(name = "注册时间")
    private Date regTime;

    /** $column.columnComment */
    @Excel(name = "星级")
    private Double star;

    /** 账户状态（可用，锁定） */
    @Excel(name = "账户状态")
    private String status;

    /** $column.columnComment */
    @Excel(name = "账户状态", readConverterExp = "$column.readConverterExp()")
    private Integer flag;

    /** $column.columnComment */
    @Excel(name = "账户状态", readConverterExp = "$column.readConverterExp()")
    private String realName;

    /** 账户级别 */
    @Excel(name = "账户级别")
    private String accountLevel;

    /** 积分 */
    @Excel(name = "积分")
    private Integer score;

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
            .toString();
    }
}
