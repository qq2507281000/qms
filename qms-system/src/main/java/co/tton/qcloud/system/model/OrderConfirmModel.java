package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-22 10:32
 */

@Data
@ApiModel("订单核销")
public class OrderConfirmModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单ID")
    private String id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("联系电话")
    private String mobile;

    @ApiModelProperty("课程标题")
    private String coursesTitle;

    @ApiModelProperty("上课时间")
    private Date lessionTime;

    @ApiModelProperty("上午下午")
    private String ampm;

    @ApiModelProperty("订单核销单号")
    private String confirmNo;

}
