package co.tton.qcloud.system.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-18 14:25
 */

@Data
@ApiModel("调用优惠券计算结果")
public class OrderPriceModel {

    /***
     * 课程Id
     */
    @ApiModelProperty("课程Id")
    private String coursesId;

    /***
     * 价格Id
     */
    @ApiModelProperty("价格Id")
    private String priceId;

    /***
     * 优惠券Id
     */
    @ApiModelProperty("优惠券Id")
    private String couponId;

    /***
     * 课程原始价格
     */
    @ApiModelProperty("课程原始价格")
    private double coursesPrice;

    /***
     * 优惠券面值
     */
    @ApiModelProperty("优惠券面值")
    private double couponFaceValue;

    /***
     * 实际应付金额
     */
    @ApiModelProperty("实际应付金额")
    private double realPrice;


}
