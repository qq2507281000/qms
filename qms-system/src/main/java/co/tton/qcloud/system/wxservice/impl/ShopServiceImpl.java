package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.mapper.TShopMapper;
import co.tton.qcloud.system.wxservice.ITShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家信息Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-23
 */
@Service
public class ShopServiceImpl implements ITShopService
{
    @Autowired
    private TShopMapper tShopMapper;

    /**
     * 首页推荐商家查询，查询所有商家，名称查询商家
     *
     * @param
     * @return 获取商家信息成功。
     */
    @Override
    public List<TShop> getSuggestShop(String categoryId,Integer suggest,String name) {
        return tShopMapper.getSuggestShop(categoryId,suggest,name);
    }

    /**
     * 查询商家详情
     *
     * @param
     * @return 获取商家详情成功。
     */
    @Override
    public TShop getShopDetail(String shopId){
        return tShopMapper.getShopDetail(shopId);
    }
}