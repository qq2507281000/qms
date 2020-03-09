package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.MainModel;
import co.tton.qcloud.system.domain.MainTrendModel;
import co.tton.qcloud.system.domain.SysUser;

public interface IDashboardService {


    /***
     * 获取首页统计数据
     * @param user 当前登录用户
     * @return
     */
    MainModel fetchRuntimeData(SysUser user);


    /***
     * 获取首页统计趋势图
     * @param user 当前登录用户
     * @return
     */
    MainTrendModel fetchTrendData(SysUser user);


}
