package co.tton.qcloud.system.service.impl;


import co.tton.qcloud.system.domain.HomePageModel;
import co.tton.qcloud.system.mapper.HomePageMapper;
import co.tton.qcloud.system.service.IHomePageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class HomePageServiceImpl implements IHomePageService {


    @Autowired
    private HomePageMapper homePageMapper;

    public HomePageModel getHomePage(String regionId) {
        HomePageModel homePageModel = new HomePageModel();
        DecimalFormat df = new DecimalFormat("0.00"); //做除法保留整数用

        //商家总量
        String shopCount = homePageMapper.getShopCount(regionId);
        homePageModel.setShopCount(shopCount);



        //获取当前时间的上一个月的日期
        Date date = new Date();
        int iyear=Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int imonth=Integer.parseInt(new SimpleDateFormat("MM").format(date))-1;
        int iday=Integer.parseInt(new SimpleDateFormat("dd").format(date));
        String year = iyear + "";
        String month = imonth + "";
        String day  = iday + "";
        String localDay = year + "-" + month + "-" + day;

        //开始计算毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        //求最近七天的日期
        List<String> lastYearSevenDays = homePageMapper.getNearSevenDays(localDay);

        //创建一个首页Echarts数据对象 订单
        List<List<String>> orderEcharts = new ArrayList<>();
        List<List<String>> salesEcharts = new ArrayList<>();

        //获取当前日期
        LocalDate nowdate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        nowdate.format(formatter);

        List<String> nearSevenDays = homePageMapper.getNearSevenDays(nowdate.toString()); //近七天的日期 yyyy-MM-dd

        int i =0;

        if (regionId !=null && regionId !=""){
            //用户总量
            String userCount = homePageMapper.getUserCountByRegionId(regionId);
            homePageModel.setUserCount(userCount);
            //订单总量
            String orderCount = homePageMapper.getOrderCountByRegionId(regionId);
            homePageModel.setOrderCount(orderCount);
            //核销订单总量
            String cancelOrderCount = homePageMapper.getCancelOrderCountByRegionId(regionId);
            homePageModel.setCancelOrderCount(cancelOrderCount);
            //订单金额
            String orderMoney = homePageMapper.getOrderMoneyByRegionId(regionId);
            homePageModel.setOrderMoney(orderMoney);
            //作日订单金额
            String yesterdayOrderMoney = homePageMapper.getYesterdayOrderMoneyByRegionId(regionId);
            homePageModel.setYesterdayOrderMoney(yesterdayOrderMoney);
            //核销金额
            String cancelMoney = homePageMapper.getCancelMoneyByRegionId(regionId);
            homePageModel.setCancelMoney(cancelMoney);
            //昨日核销金额
            String yesterdayCancelMoney = homePageMapper.getYesterdayCancelMoneyByRegionId(regionId);
            homePageModel.setYesterdayCancelMoney(yesterdayCancelMoney);
            //新增用户数
            String newUserCount = homePageMapper.getNewUserCountByRegionId(regionId);
            homePageModel.setNewUserCount(newUserCount);
            //作日新增用户数
            String yesterdayNewUserCount = homePageMapper.getYesterdayNewUserCountByRegionId(regionId);
            homePageModel.setYesterdayNewUserCount(yesterdayNewUserCount);
            //下单用户数
            String buyCount = homePageMapper.getBuyCountByRegionId(regionId);
            homePageModel.setBuyCount(buyCount);
            //昨日下单用户数
            String yesterdayBuyCount = homePageMapper.getYesterdayBuyCountByRegionId(regionId);
            homePageModel.setYesterdayBuyCount(yesterdayBuyCount);
            //最近一个月增长用户数
            String monthUsers = homePageMapper.getMonthUsersByRegionId(regionId);
            homePageModel.setMonthUsers(monthUsers);
            String beforeMonthUsers = homePageMapper.getBeforeMonthUsersByRegionId(regionId); //刨除本月之外的用户总和
            //最近一个月增长用户数百分比
            if(beforeMonthUsers.equals("0")){
                String monthUsersPercent = df.format((float)Integer.parseInt(monthUsers)*100) + "%"; //计算最近一个月增长用户数百分比
                homePageModel.setMonthUsersPercent(monthUsersPercent);
            }else {
                String monthUsersPercent = df.format((float) Integer.parseInt(monthUsers) / Integer.parseInt(beforeMonthUsers) * 100) + "%"; //计算最近一个月增长用户数百分比
                homePageModel.setMonthUsersPercent(monthUsersPercent);
            }
            //最近一个月订单
            String monthOrders = homePageMapper.getMonthOrdersByRegionId(regionId);
            homePageModel.setMonthOrders(monthOrders);
            String beforeMonthOrders = homePageMapper.getBeforeMonthOrdersByRegionId(regionId); //刨除本月之外的订单总和
            //最近一个月订单百分比
            if(beforeMonthOrders.equals("0")){
                String monthOrdersPercent = df.format((float)Integer.parseInt(monthOrders)*100) + "%"; //计算最近一个月增长用户数百分比
                homePageModel.setMonthOrdersPercent(monthOrdersPercent);
            }else {
                String monthOrdersPercent = df.format((float) Integer.parseInt(monthOrders) / Integer.parseInt(beforeMonthOrders) * 100) + "%"; //计算最近一个月增长用户数百分比
                homePageModel.setMonthOrdersPercent(monthOrdersPercent);
            }
            //最近一个月销售额
            String monthSolds = homePageMapper.getMonthSoldsByRegionId(regionId);
            homePageModel.setMonthSolds(monthSolds);
            String beforeMonthSolds = homePageMapper.getBeforeMonthSoldsByRegionId(regionId);  //刨除本月之外的销售额总和
            float floatMonthSolds = Float.parseFloat(monthSolds);
            float floatBeforeMonthSolds = Float.parseFloat(beforeMonthSolds);
            //最近一个月销售额百分比
            if(beforeMonthSolds.equals("0")){
                //String monthSoldsPercent = df.format((float)Integer.parseInt(monthSolds)*100) + "%"; //计算最近一个月增长用户数百分比
                String monthSoldsPercent = df.format(Math.round(Float.parseFloat(monthSolds)*100)) + "%";
                homePageModel.setMonthSoldsPercent(monthSoldsPercent);
            }else {
                String monthSoldsPercent =  df.format(floatMonthSolds / floatBeforeMonthSolds * 100) + "%";
                homePageModel.setMonthSoldsPercent(monthSoldsPercent);
            }

            //ECHARTS
            for(String d:lastYearSevenDays){
                List<String> list=new ArrayList<>();  // 存储 最近七天的订单量 空对象
                List<String> saleList=new ArrayList<>();  // 存储 最近七天的付款量 空对象
                String arr[] = d.split("-");
                Integer nearYear = Integer.parseInt(arr[0]);
                Integer nearMonth = Integer.parseInt(arr[1]);
                Integer nearDay = Integer.parseInt(arr[2]);
                calendar.set(nearYear,nearMonth,nearDay);
                long millis = calendar.getTimeInMillis(); //获取毫秒时间
                list.add(Long.toString(millis));  //订单数组-添加时间
                saleList.add(Long.toString(millis)); //付款数组- 添加时间
                while (i<7){
                    String nearOrderCount = homePageMapper.getNearSevenDaysOrderCountByRegionId(nearSevenDays.get(i),regionId);
                    String nearSalesCount = homePageMapper.getNearSevenDaysSalesCountByRegionId(nearSevenDays.get(i),regionId);
                    list.add(nearOrderCount);
                    saleList.add(nearSalesCount);

                    orderEcharts.add(list);
                    salesEcharts.add(saleList);
                    break;
                }
                i++;
            }


            homePageModel.setOrderEcharts(orderEcharts);
            homePageModel.setSalesEcharts(salesEcharts);
        }else {
            //用户总量
            String userCount = homePageMapper.getUserCount();
            homePageModel.setUserCount(userCount);
            //订单总量
            String orderCount = homePageMapper.getOrderCount();
            homePageModel.setOrderCount(orderCount);
            //核销订单总量
            String cancelOrderCount = homePageMapper.getCancelOrderCount();
            homePageModel.setCancelOrderCount(cancelOrderCount);
            //订单金额
            String orderMoney = homePageMapper.getOrderMoney();
            homePageModel.setOrderMoney(orderMoney);
            //作日订单金额
            String yesterdayOrderMoney = homePageMapper.getYesterdayOrderMoney();
            homePageModel.setYesterdayOrderMoney(yesterdayOrderMoney);
            //核销金额
            String cancelMoney = homePageMapper.getCancelMoney();
            homePageModel.setCancelMoney(cancelMoney);
            //昨日核销金额
            String yesterdayCancelMoney = homePageMapper.getYesterdayCancelMoney();
            homePageModel.setYesterdayCancelMoney(yesterdayCancelMoney);
            //新增用户数
            String newUserCount = homePageMapper.getNewUserCount();
            homePageModel.setNewUserCount(newUserCount);
            //作日新增用户数
            String yesterdayNewUserCount = homePageMapper.getYesterdayNewUserCount();
            homePageModel.setYesterdayNewUserCount(yesterdayNewUserCount);
            //下单用户数
            String buyCount = homePageMapper.getBuyCount();
            homePageModel.setBuyCount(buyCount);
            //昨日下单用户数
            String yesterdayBuyCount = homePageMapper.getYesterdayBuyCount();
            homePageModel.setYesterdayBuyCount(yesterdayBuyCount);
            //最近一个月增长用户数
            String monthUsers = homePageMapper.getMonthUsers();
            homePageModel.setMonthUsers(monthUsers);
            String beforeMonthUsers = homePageMapper.getBeforeMonthUsers(); //刨除本月之外的用户总和
            //最近一个月增长用户数百分比
            String monthUsersPercent = df.format((float) Integer.parseInt(monthUsers) / Integer.parseInt(beforeMonthUsers) * 100) + "%"; //计算最近一个月增长用户数百分比
            homePageModel.setMonthUsersPercent(monthUsersPercent);
            //最近一个月订单
            String monthOrders = homePageMapper.getMonthOrders();
            homePageModel.setMonthOrders(monthOrders);
            String beforeMonthOrders = homePageMapper.getBeforeMonthOrders(); //刨除本月之外的订单总和
            //最近一个月订单百分比
            String monthOrdersPercent = df.format((float) Integer.parseInt(monthOrders) / Integer.parseInt(beforeMonthOrders) * 100) + "%";
            homePageModel.setMonthOrdersPercent(monthOrdersPercent);
            //最近一个月销售额
            String monthSolds = homePageMapper.getMonthSolds();
            String beforeMonthSolds = homePageMapper.getBeforeMonthSolds();  //刨除本月之外的销售额总和
            float floatMonthSolds = Float.parseFloat(monthSolds);
            float floatBeforeMonthSolds = Float.parseFloat(beforeMonthSolds);
            //最近一个月销售额百分比
            String monthSoldsPercent =  df.format(floatMonthSolds / floatBeforeMonthSolds * 100) + "%";
            homePageModel.setMonthSolds(monthSolds);
            homePageModel.setMonthSoldsPercent(monthSoldsPercent);

            //ECHARTS
            for(String d:lastYearSevenDays){
                List<String> list=new ArrayList<>();  // 存储 最近七天的订单量 空对象
                List<String> saleList=new ArrayList<>();  // 存储 最近七天的付款量 空对象
                String arr[] = d.split("-");
                Integer nearYear = Integer.parseInt(arr[0]);
                Integer nearMonth = Integer.parseInt(arr[1]);
                Integer nearDay = Integer.parseInt(arr[2]);
                calendar.set(nearYear,nearMonth,nearDay);
                long millis = calendar.getTimeInMillis(); //获取毫秒时间
                list.add(Long.toString(millis));  //订单数组-添加时间
                saleList.add(Long.toString(millis)); //付款数组- 添加时间
                while (i<7){
                    String nearOrderCount = homePageMapper.getNearSevenDaysOrderCount(nearSevenDays.get(i));
                    String nearSalesCount = homePageMapper.getNearSevenDaysSalesCount(nearSevenDays.get(i));
                    list.add(nearOrderCount);
                    saleList.add(nearSalesCount);

                    orderEcharts.add(list);
                    salesEcharts.add(saleList);
                    break;
                }
                i++;
            }


            homePageModel.setOrderEcharts(orderEcharts);
            homePageModel.setSalesEcharts(salesEcharts);
        }




        return homePageModel;
    }



}
