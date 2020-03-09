package co.tton.qcloud.system.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2020-03-08 15:47
 */

@Data
@ApiModel("首页统计数据")
public class MainModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * 显示Title
     */
    private String title;

    /***
     * 课程总量
     */
    private String coursesCount;

    /***
     * 与上周对比增量百分比
     */
    private String coursesUpPercent;

    /***
     * 会员数量
     */
    private String memberCount;

    /***
     * 会员与上周对比增量百分比
     */
    private String memberUpPercent;

    /***
     * 订单数量
     */
    private String orderCount;

    /***
     * 订单数量与上周对比增量百分比
     */
    private String orderUpPercent;

    /***
     * 评论数量
     */
    private String commentCount;

    /***
     * 评论数量与上周对比增量百分比
     */
    private String commentUpPercent;

    /***
     * 销售额
     */
    private String saleAmount;

    /***
     * 已支付订单数
     */
    private String paidOrderAmount;

    /***
     * 今日新增用户数
     */
    private String todayMemberCount;

    /***
     * 昨日新增用户数
     */
    private String yestodayMemberCount;


    /***
     * 下单用户数
     */
    private String todayPaidMemberAmount;

    /***
     * 昨日下单用户数
     */
    private String yestodayPaidMemberAmount;

    /***
     * 店铺Ids
     */
    private List<String> shopIds;

}
