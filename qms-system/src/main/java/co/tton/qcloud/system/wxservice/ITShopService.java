package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TShop;

import java.util.List;

/**
 * 商家信息Service接口
 * 
 * @author qcloud
 * @date 2019-09-23
 */
public interface ITShopService 
{
    /**
     * 首页推荐商家查询，查询所有商家
     * 
     * @param
     * @return 获取推荐商家信息成功。
     */
    List<TShop> getSuggestShop(TShop tShop);

    /**
     * 查询所有商家
     *
     * @param
     * @return 获取商家信息成功。
     */
    List<TShop> getSuggestShopAll(TShop tShop);

    /**
     * 查询分类所有商家
     *
     * @param
     * @return 获取推荐商家信息成功。
     */
    List<TShop> getSuggestShopByCategory(TShop tShop);



    /**
     * 查询商家详情
     *
     * @param
     * @return 获取商家详情成功。
     */
    List getShopDetail(TShop tShop);

    /**
     * 模糊查询商家
     *
     * @param
     * @return 获取商家详情
     */
    List<TShop> getNameShop(String name);
}
