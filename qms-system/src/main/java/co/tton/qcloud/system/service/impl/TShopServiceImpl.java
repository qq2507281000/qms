package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.system.model.ShopCenterModel;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TShopMapper;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.service.ITShopService;

import javax.annotation.Resource;

/**
 * 商家信息Service业务层处理
 *
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopServiceImpl implements ITShopService
{
    @Resource
    private TShopMapper tShopMapper;

    /**
     * 查询商家信息
     *
     * @param id 商家信息ID
     * @return 商家信息
     */
    @Override
    public TShop selectTShopById(String id)
    {
        return tShopMapper.selectTShopById(id);
    }

    /**
     * 查询商家信息列表
     *
     * @param tShop 商家信息
     * @return 商家信息
     */
    @Override
    public List<TShop> selectTShopList(TShop tShop)
    {
        tShop.setFlag(0);
        tShop.setAvailable(0);
        return tShopMapper.selectTShopList(tShop);
    }

    /**
     * 新增商家信息
     *
     * @param tShop 商家信息
     * @return 结果
     */
    @Override
    public int insertTShop(TShop tShop)
    {
        return tShopMapper.insertTShop(tShop);
    }

    /**
     * 修改商家信息
     *
     * @param tShop 商家信息
     * @return 结果
     */
    @Override
    public int updateTShop(TShop tShop)
    {
        tShop.setUpdateTime(DateUtils.getNowDate());
        return tShopMapper.updateTShop(tShop);
    }

    /**
     * 删除商家信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTShopByIds(String ids)
    {
        return tShopMapper.deleteTShopByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除商家信息信息
     *
     * @param id 商家信息ID
     * @return 结果
     */
    @Override
    public int deleteTShopById(String id)
    {
        return tShopMapper.deleteTShopById(id);
    }

    /**
     * 微信公众号查询商户信息
     * @param id id
     * @return
     */
    @Override
    public ShopCenterModel selectWPShopCenterById(String id) {
        return tShopMapper.selectWPShopCenterById(id);
    }


}
