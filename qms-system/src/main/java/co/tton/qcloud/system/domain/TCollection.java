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
 * 收藏夹对象 t_collection
 *
 * @author qcloud
 * @date 2019-09-05
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序收藏夹对象")
public class TCollection extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("主键")
    private String id;

    /** $column.columnComment */
    @ApiModelProperty("会员信息Id")
    @Excel(name = "会员信息Id")
    private String memberId;

    /** $column.columnComment */
    @ApiModelProperty("课程Id")
    @Excel(name = "课程Id")
    private String coursesId;

    /** $column.columnComment */
    @Excel(name = "数据状态", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("数据状态")
    private Integer flag;


    @Override
    public String toString() {
      return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
          .append("id", getId())
          .append("memberId", getMemberId())
          .append("coursesId", getCoursesId())
          .append("flag", getFlag())
          .append("createTime", getCreateTime())
          .append("createBy", getCreateBy())
          .append("updateTime", getUpdateTime())
          .append("updateBy", getUpdateBy())
          .toString();
    }
}
