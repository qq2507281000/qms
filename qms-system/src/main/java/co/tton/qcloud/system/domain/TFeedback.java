package co.tton.qcloud.system.domain;

import co.tton.qcloud.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("小程序反馈信息")
public class TFeedback extends BaseEntity {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("反馈内容")
    private String content;


}
