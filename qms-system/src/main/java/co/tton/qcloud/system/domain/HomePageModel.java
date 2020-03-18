package co.tton.qcloud.system.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("首页统计数据")
public class HomePageModel implements Serializable {

    /***
     * 商家总量
     */
    private String shopCount;

    /***
     * 用户总量
     */
    private String userCount;

    /***
     * 订单总量
     */
    private String orderCount;

    /***
     * 核销订单总量
     */
    private String cancelOrderCount;

    /***
     * 订单金额
     */
    private String orderMoney;

    /***
     * 作日订单金额
     */
    private String yesterdayOrderMoney;

    /***
     * 核销金额
     */
    private String cancelMoney;

    /***
     * 昨日核销金额
     */
    private String yesterdayCancelMoney;

    /***
     * 新增用户数
     */
    private String newUserCount;

    /***
     * 作日新增用户数
     */
    private String yesterdayNewUserCount;

    /***
     * 下单用户数
     */
    private String buyCount;

    /***
     * 昨日下单用户数
     */
    private String yesterdayBuyCount;

    /***
     * 最近一个月增长用户数
     */
    private String monthUsers;

    /***
     * 最近一个月增长用户数百分比
     */
    private String monthUsersPercent;

    /***
     * 最近一个月订单
     */
    private String monthOrders;

    /***
     * 最近一个月订单百分比
     */
    private String monthOrdersPercent;

    /***
     * 最近一个月销售额
     */
    private String monthSolds;

    /***
     * 最近一个月销售额百分比
     */
    private String monthSoldsPercent;

    /***
     * 最近七天的订单量
     */
    private  List<List<String>> orderEcharts;

    /***
     * 最近七天的付款量
     */
    private  List<List<String>> salesEcharts;
}
