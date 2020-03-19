package co.tton.qcloud.system.mapper;

import java.util.List;

public interface HomePageMapper {


    String getShopCount(String regionId);

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

    String getUserCountByRegionId(String regionId);

    String getOrderCountByRegionId(String regionId);

    String getCancelOrderCountByRegionId(String regionId);

    String getOrderMoneyByRegionId(String regionId);

    String getYesterdayOrderMoneyByRegionId(String regionId);

    String getCancelMoneyByRegionId(String regionId);

    String getYesterdayCancelMoneyByRegionId(String regionId);

    String getNewUserCountByRegionId(String regionId);

    String getYesterdayNewUserCountByRegionId(String regionId);

    String getBuyCountByRegionId(String regionId);

    String getYesterdayBuyCountByRegionId(String regionId);

    String getMonthUsersByRegionId(String regionId);

    String getBeforeMonthUsersByRegionId(String regionId);

    String getMonthOrdersByRegionId(String regionId);

    String getBeforeMonthOrdersByRegionId(String regionId);

    String getMonthSoldsByRegionId(String regionId);

    String getBeforeMonthSoldsByRegionId(String regionId);

    String getNearSevenDaysOrderCountByRegionId(String date, String regionId);

    String getNearSevenDaysSalesCountByRegionId(String date, String regionId);
}
