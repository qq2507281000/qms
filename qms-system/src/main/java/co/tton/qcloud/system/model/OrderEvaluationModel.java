package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-14 20:09
 */

@Data
@ApiModel("订单（课程）评价前端传递对象")
public class OrderEvaluationModel {

    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("课程Id")
    private String coursesId;

    @ApiModelProperty("星级")
    private int star;

    @ApiModelProperty("评价内容")
    private String comment;

    @ApiModelProperty("评价图片")
    private String imageUrls;

    @ApiModelProperty("当前登录用户Id")
    private String openId;

}
