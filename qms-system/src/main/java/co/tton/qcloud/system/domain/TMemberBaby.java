package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.annotation.Excel;
import co.tton.qcloud.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * 【请填写功能名称】对象 t_member_baby
 * 
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TMemberBaby extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "会员Id", readConverterExp = "$column.readConverterExp()")
    private String memberId;

    /** $column.columnComment */
    @Excel(name = "真实姓名", readConverterExp = "$column.readConverterExp()")
    private String realName;

    /** $column.columnComment */
    @Excel(name = "出生日期", readConverterExp = "$column.readConverterExp()")
    private Date birthday;

    /** $column.columnComment */
    @Excel(name = "性别", readConverterExp = "$column.readConverterExp()")
    private Integer sex;

    /** 兴趣，以逗号分隔。 */
    @Excel(name = "兴趣，以逗号分隔。")
    private String interest;

    /** 昵称，小名。 */
    @Excel(name = "昵称，小名。")
    private String nickName;

    /** $column.columnComment */
    @Excel(name = "数据状态")
    private Integer flag;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberId", getMemberId())
            .append("realName", getRealName())
            .append("birthday", getBirthday())
            .append("sex", getSex())
            .append("interest", getInterest())
            .append("nickName", getNickName())
            .append("flag", getFlag())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
