package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序反馈信息")
public class TFeedback extends BaseEntity {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("反馈内容")
    private String content;

    @ApiModelProperty("数据状态")
    private Integer flag;

    @ApiModelProperty("反馈内容")
    private String memberId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("flag", getFlag())
            .append("memberId", getMemberId())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }

}
