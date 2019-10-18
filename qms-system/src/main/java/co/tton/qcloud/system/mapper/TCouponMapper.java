package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TCoupon;
import java.util.List;

/**
 * 平台优惠券Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TCouponMapper 
{
    /**
     * 查询平台优惠券
     * 
     * @param id 平台优惠券ID
     * @return 平台优惠券
     */
    public TCoupon selectTCouponById(String id);

    /**
     * 查询平台优惠券列表
     * 
     * @param tCoupon 平台优惠券
     * @return 平台优惠券集合
     */
    public List<TCoupon> selectTCouponList(TCoupon tCoupon);

    /***
     * 查询用户领用的优惠券信息
     * @param memberId
     * @return
     */
    List<TCoupon> selectTCouponByMemberId(String memberId);

    /**
     * 新增平台优惠券
     * 
     * @param tCoupon 平台优惠券
     * @return 结果
     */
    public int insertTCoupon(TCoupon tCoupon);

    /**
     * 修改平台优惠券
     * 
     * @param tCoupon 平台优惠券
     * @return 结果
     */
    public int updateTCoupon(TCoupon tCoupon);

    /**
     * 删除平台优惠券
     * 
     * @param id 平台优惠券ID
     * @return 结果
     */
    public int deleteTCouponById(String id);

    /**
     * 批量删除平台优惠券
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCouponByIds(String[] ids);
}
