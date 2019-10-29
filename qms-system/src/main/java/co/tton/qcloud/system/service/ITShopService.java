package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.model.ShopCenterModel;

import java.util.List;

/**
 * 商家信息Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopService 
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
     * 批量删除商家信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTShopByIds(String ids);

    /**
     * 删除商家信息信息
     * 
     * @param id 商家信息ID
     * @return 结果
     */
    public int deleteTShopById(String id);

    /**
     * 微信公众号查询商户信息
     * @param id id
     * @return
     */
    public ShopCenterModel selectWPShopCenterById(String id);
}
