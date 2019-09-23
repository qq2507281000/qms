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
     * 商家查询
     *
     * @param
     * @return 获取商家信息成功。
     */
    @Override
    public List<TShop> getSuggestShop(String categoryId,Integer suggest) {
        TShop tShop =new TShop();
        tShop.setCategoryId(categoryId);
        tShop.setSuggest(suggest);
        return tShopMapper.getSuggestShop(tShop);
    }

    /**
     * 查询商家详情
     *
     * @param
     * @return 获取商家详情成功。
     */
    @Override
    public TShop getShopDetail(String shopId){
        TShop tShop =new TShop();
        tShop.setId(shopId);
        return tShopMapper.getShopDetail(tShop);
    }
}
