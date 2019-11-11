package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.mapper.TShopMapper;
import co.tton.qcloud.system.wxservice.ITShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private TShopMapper tShopMapper;

    /**
     * 首页推荐商家查询，查询所有商家
     *
     * @param
     * @return 获取商家信息成功。
     */
    @Override
    public List<TShop> getSuggestShop(TShop tShop) {
        return tShopMapper.getSuggestShop(tShop);
    }

    /**
     * 首页推荐商家查询，查询所有商家
     *
     * @param
     * @return 获取商家信息成功。
     */
    @Override
    public List<TShop> getSuggestShopByCategory(TShop tShop) {
        return tShopMapper.getSuggestShopByCategory(tShop);
    }



    /**
     * 查询商家详情
     *
     * @param
     * @return 获取商家详情成功。
     */
    @Override
    public List getShopDetail(TShop tShop){
        return tShopMapper.getShopDetail(tShop);
    }

    /**
     * 模糊查询商家
     *
     * @param
     * @return 获取商家详情
     */
    @Override
    public List<TShop> getNameShop(String name) {
        return tShopMapper.getNameShop(name);
    }
}
