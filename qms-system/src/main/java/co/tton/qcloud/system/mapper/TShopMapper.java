package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.model.ShopCenterModel;

import java.util.List;

/**
 * 商家信息Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TShopMapper 
{
    /**
     * 查询商家信息
     * 
     * @param id 商家信息ID
     * @return 商家信息
     */
    public TShop selectTShopById(String id);

    /**
     * 查询商家信息列表
     * 
     * @param tShop 商家信息
     * @return 商家信息集合
     */
    public List<TShop> selectTShopList(TShop tShop);

    /**
     * 新增商家信息
     * 
     * @param tShop 商家信息
     * @return 结果
     */
    public int insertTShop(TShop tShop);

    /**
     * 修改商家信息
     * 
     * @param tShop 商家信息
     * @return 结果
     */
    public int updateTShop(TShop tShop);

    /**
     * 删除商家信息
     * 
     * @param id 商家信息ID
     * @return 结果
     */
    public int deleteTShopById(String id);

    /**
     * 批量删除商家信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTShopByIds(String[] ids);

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
     * @return 查询所有商家。
     */
    List<TShop> getSuggestShopAll(TShop tShop);

    /**
     * 首页推荐商家查询，查询所有商家
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
    List<TShop> getNameShop(TShop tShop);

    /**
     * 微信公众号查询商户信息
     * @param id
     * @return
     */
    ShopCenterModel selectWPShopCenterById(String id);
}
