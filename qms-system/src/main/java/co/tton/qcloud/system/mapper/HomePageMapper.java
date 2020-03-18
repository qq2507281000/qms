package co.tton.qcloud.system.mapper;

import java.util.List;

public interface HomePageMapper {


    String getShopCount();

    String getUserCount();

    String getOrderCount();

    String getCancelOrderCount();

    String getOrderMoney();

    String getYesterdayOrderMoney();

    String getCancelMoney();

    String getYesterdayCancelMoney();

    String getNewUserCount();

    String getYesterdayNewUserCount();

    String getBuyCount();

    String getYesterdayBuyCount();

    String getMonthUsers();

    String getMonthOrders();

    String getMonthSolds();

    List<String> getNearSevenDays(String dayday);

    String getNearSevenDaysOrderCount(String date);

    String getNearSevenDaysSalesCount(String date);

    String getBeforeMonthUsers();

    String getBeforeMonthOrders();

    String getBeforeMonthSolds();
}
