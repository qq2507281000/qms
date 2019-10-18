package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TCouponMapper;
import co.tton.qcloud.system.domain.TCoupon;
import co.tton.qcloud.system.service.ITCouponService;

/**
 * 平台优惠券Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TCouponServiceImpl implements ITCouponService 
{
    @Autowired
    private TCouponMapper tCouponMapper;

    /**
     * 查询平台优惠券
     * 
     * @param id 平台优惠券ID
     * @return 平台优惠券
     */
    @Override
    public TCoupon selectTCouponById(String id)
    {
        return tCouponMapper.selectTCouponById(id);
    }

    /**
     * 查询平台优惠券列表
     * 
     * @param tCoupon 平台优惠券
     * @return 平台优惠券
     */
    @Override
    public List<TCoupon> selectTCouponList(TCoupon tCoupon)
    {
        return tCouponMapper.selectTCouponList(tCoupon);
    }

    /***
     * 查询用户领用的优惠券信息
     * @param memberId
     * @return
     */
    @Override
    public List<TCoupon> selectTCouponByMemberId(String memberId) {
        return tCouponMapper.selectTCouponByMemberId(memberId);
    }

    /**
     * 新增平台优惠券
     * 
     * @param tCoupon 平台优惠券
     * @return 结果
     */
    @Override
    public int insertTCoupon(TCoupon tCoupon)
    {
        tCoupon.setCreateTime(DateUtils.getNowDate());
        return tCouponMapper.insertTCoupon(tCoupon);
    }

    /**
     * 修改平台优惠券
     * 
     * @param tCoupon 平台优惠券
     * @return 结果
     */
    @Override
    public int updateTCoupon(TCoupon tCoupon)
    {
        tCoupon.setUpdateTime(DateUtils.getNowDate());
        return tCouponMapper.updateTCoupon(tCoupon);
    }

    /**
     * 删除平台优惠券对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTCouponByIds(String ids)
    {
        return tCouponMapper.deleteTCouponByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除平台优惠券信息
     * 
     * @param id 平台优惠券ID
     * @return 结果
     */
    public int deleteTCouponById(String id)
    {
        return tCouponMapper.deleteTCouponById(id);
    }
}
