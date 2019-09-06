package co.tton.qcloud.system.service.impl;

import java.util.List;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TCouponGrantMapper;
import co.tton.qcloud.system.domain.TCouponGrant;
import co.tton.qcloud.system.service.ITCouponGrantService;

/**
 * 优惠券生成参数库Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TCouponGrantServiceImpl implements ITCouponGrantService 
{
    @Autowired
    private TCouponGrantMapper tCouponGrantMapper;

    /**
     * 查询优惠券生成参数库
     * 
     * @param id 优惠券生成参数库ID
     * @return 优惠券生成参数库
     */
    @Override
    public TCouponGrant selectTCouponGrantById(String id)
    {
        return tCouponGrantMapper.selectTCouponGrantById(id);
    }

    /**
     * 查询优惠券生成参数库列表
     * 
     * @param tCouponGrant 优惠券生成参数库
     * @return 优惠券生成参数库
     */
    @Override
    public List<TCouponGrant> selectTCouponGrantList(TCouponGrant tCouponGrant)
    {
        return tCouponGrantMapper.selectTCouponGrantList(tCouponGrant);
    }

    /**
     * 新增优惠券生成参数库
     * 
     * @param tCouponGrant 优惠券生成参数库
     * @return 结果
     */
    @Override
    public int insertTCouponGrant(TCouponGrant tCouponGrant)
    {
        tCouponGrant.setCreateTime(DateUtils.getNowDate());
        return tCouponGrantMapper.insertTCouponGrant(tCouponGrant);
    }

    /**
     * 修改优惠券生成参数库
     * 
     * @param tCouponGrant 优惠券生成参数库
     * @return 结果
     */
    @Override
    public int updateTCouponGrant(TCouponGrant tCouponGrant)
    {
        tCouponGrant.setUpdateTime(DateUtils.getNowDate());
        return tCouponGrantMapper.updateTCouponGrant(tCouponGrant);
    }

    /**
     * 删除优惠券生成参数库对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTCouponGrantByIds(String ids)
    {
        return tCouponGrantMapper.deleteTCouponGrantByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除优惠券生成参数库信息
     * 
     * @param id 优惠券生成参数库ID
     * @return 结果
     */
    public int deleteTCouponGrantById(String id)
    {
        return tCouponGrantMapper.deleteTCouponGrantById(id);
    }
}
