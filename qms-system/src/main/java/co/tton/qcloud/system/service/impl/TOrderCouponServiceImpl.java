package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TOrderCouponMapper;
import co.tton.qcloud.system.domain.TOrderCoupon;
import co.tton.qcloud.system.service.ITOrderCouponService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TOrderCouponServiceImpl implements ITOrderCouponService 
{
    @Autowired
    private TOrderCouponMapper tOrderCouponMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TOrderCoupon selectTOrderCouponById(String id)
    {
        return tOrderCouponMapper.selectTOrderCouponById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrderCoupon 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TOrderCoupon> selectTOrderCouponList(TOrderCoupon tOrderCoupon)
    {
        return tOrderCouponMapper.selectTOrderCouponList(tOrderCoupon);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrderCoupon 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTOrderCoupon(TOrderCoupon tOrderCoupon)
    {
        tOrderCoupon.setCreateTime(DateUtils.getNowDate());
        return tOrderCouponMapper.insertTOrderCoupon(tOrderCoupon);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrderCoupon 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTOrderCoupon(TOrderCoupon tOrderCoupon)
    {
        tOrderCoupon.setUpdateTime(DateUtils.getNowDate());
        return tOrderCouponMapper.updateTOrderCoupon(tOrderCoupon);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOrderCouponByIds(String ids)
    {
        return tOrderCouponMapper.deleteTOrderCouponByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderCouponById(String id)
    {
        return tOrderCouponMapper.deleteTOrderCouponById(id);
    }
}
