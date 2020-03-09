package co.tton.qcloud.system.service.impl;

import co.tton.qcloud.system.domain.MainModel;
import co.tton.qcloud.system.domain.MainTrendModel;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.mapper.SysUserMapper;
import co.tton.qcloud.system.mapper.TShopMapper;
import co.tton.qcloud.system.service.IDashboardService;
import com.github.pagehelper.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2020-03-08 15:46
 */

@Service
@Slf4j
@AllArgsConstructor
public class DashboardServiceImpl implements IDashboardService {

    private SysUserMapper userMapper;

    private TShopMapper shopMapper;

    @Override
    public MainModel fetchRuntimeData(SysUser user) {

        Long userId = user.getUserId();

        try{
            List<String> shopIds = getShopIdsByUserId(user);
            if(shopIds == null){
                log.error("未能获取当前登录用户授权的商户ID列表");
            }
            else{
                if(shopIds.size() == 0){
                    log.error("未能获取当前登录用户授权的商户ID列表");
                }
                else{

                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            log.error("获取首页统计数据时发生异常。",ex);
        }

        return null;
    }

    @Override
    public MainTrendModel fetchTrendData(SysUser user) {
        return null;
    }


    /***
     * 获取当前登录用户授权范围内的商铺ID列表
     * @param user
     * @return
     */
    private List<String> getShopIdsByUserId(SysUser user){
        if(StringUtils.equals(user.getCategory(),"ADMIN")){
            // 超级管理员，获取所有商家的数据
            TShop shop = new TShop();
            shop.setFlag(0);
            return shopMapper.selectTShopList(shop).stream().map(d->d.getId()).collect(Collectors.toList());
        }
        else if(StringUtils.equals(user.getCategory(),"REGION")){
            // 区域管理员，获取区域内所有商家
            TShop shop = new TShop();
            shop.setFlag(0);
            shop.setRegionId(user.getBusinessId());
            return shopMapper.selectTShopList(shop).stream().map(d->d.getId()).collect(Collectors.toList());
        }
        else{
            return Collections.emptyList();
        }
    }
}
