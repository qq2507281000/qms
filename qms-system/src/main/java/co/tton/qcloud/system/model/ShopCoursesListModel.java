package co.tton.qcloud.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: qms
 * @description: 最近上新课程信息
 * @author: Rain@TTON
 * @create: 2019-10-28 17:18
 */

@Data
@ApiModel("课程列表对象")
public class ShopCoursesListModel {

    /***
     * 商家Id
     */
    @ApiModelProperty("商家Id")
    private String shopId;

    /***
     * 课程Id
     */
    @ApiModelProperty("课程Id")
    private String coursesId;

    /***
     * 封面图片
     */
    @ApiModelProperty("封面图片")
    private String coverImage;

    /***
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /***
     * 商家名称
     */
    @ApiModelProperty("商家名称")
    private String shopName;

    /***
     * 月销
     */
    @ApiModelProperty("月销")
    private int saleCount;

    /***
     * 价格
     */
    @ApiModelProperty("价格")
    private double startPrice;

}
