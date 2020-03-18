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

    public HomePageModel getHomePage() {
        HomePageModel homePageModel = new HomePageModel();


        String shopCount = homePageMapper.getShopCount();
        String userCount = homePageMapper.getUserCount();
        String orderCount = homePageMapper.getOrderCount();
        String cancelOrderCount = homePageMapper.getCancelOrderCount();
        String orderMoney = homePageMapper.getOrderMoney();
        String yesterdayOrderMoney = homePageMapper.getYesterdayOrderMoney();
        String cancelMoney = homePageMapper.getCancelMoney();
        String yesterdayCancelMoney = homePageMapper.getYesterdayCancelMoney();
        String newUserCount = homePageMapper.getNewUserCount();
        String yesterdayNewUserCount = homePageMapper.getYesterdayNewUserCount();
        String buyCount = homePageMapper.getBuyCount();
        String yesterdayBuyCount = homePageMapper.getYesterdayBuyCount();

        DecimalFormat df = new DecimalFormat("0.00"); //做除法保留整数用

        String monthUsers = homePageMapper.getMonthUsers();
        String beforeMonthUsers = homePageMapper.getBeforeMonthUsers(); //刨除本月之外的用户总和
        String monthUsersPercent = df.format((float) Integer.parseInt(monthUsers) / Integer.parseInt(beforeMonthUsers) * 100) + "%"; //计算最近一个月增长用户数百分比


        String monthOrders = homePageMapper.getMonthOrders();
        String beforeMonthOrders = homePageMapper.getBeforeMonthOrders(); //刨除本月之外的订单总和
        String monthOrdersPercent = df.format((float) Integer.parseInt(monthOrders) / Integer.parseInt(beforeMonthOrders) * 100) + "%";


        String monthSolds = homePageMapper.getMonthSolds();
        String beforeMonthSolds = homePageMapper.getBeforeMonthSolds();  //刨除本月之外的销售额总和
        float floatMonthSolds = Float.parseFloat(monthSolds);
        float floatBeforeMonthSolds = Float.parseFloat(beforeMonthSolds);
        String monthSoldsPercent =  df.format(floatMonthSolds / floatBeforeMonthSolds * 100) + "%";






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


        homePageModel.setShopCount(shopCount);
        homePageModel.setUserCount(userCount);
        homePageModel.setOrderCount(orderCount);
        homePageModel.setCancelOrderCount(cancelOrderCount);
        homePageModel.setOrderMoney(orderMoney);
        homePageModel.setYesterdayOrderMoney(yesterdayOrderMoney);
        homePageModel.setCancelMoney(cancelMoney);
        homePageModel.setYesterdayCancelMoney(yesterdayCancelMoney);
        homePageModel.setNewUserCount(newUserCount);
        homePageModel.setYesterdayNewUserCount(yesterdayNewUserCount);
        homePageModel.setBuyCount(buyCount);
        homePageModel.setYesterdayBuyCount(yesterdayBuyCount);
        homePageModel.setMonthUsers(monthUsers);
        homePageModel.setMonthOrders(monthOrders);
        homePageModel.setMonthSolds(monthSolds);
        homePageModel.setOrderEcharts(orderEcharts);
        homePageModel.setSalesEcharts(salesEcharts);
        homePageModel.setMonthUsersPercent(monthUsersPercent);
        homePageModel.setMonthOrdersPercent(monthOrdersPercent);
        homePageModel.setMonthSoldsPercent(monthSoldsPercent);

        return homePageModel;
    }



}
