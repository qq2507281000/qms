package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TOrderCoupon;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TOrderCouponMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TOrderCoupon selectTOrderCouponById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrderCoupon 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TOrderCoupon> selectTOrderCouponList(TOrderCoupon tOrderCoupon);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrderCoupon 【请填写功能名称】
     * @return 结果
     */
    public int insertTOrderCoupon(TOrderCoupon tOrderCoupon);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrderCoupon 【请填写功能名称】
     * @return 结果
     */
    public int updateTOrderCoupon(TOrderCoupon tOrderCoupon);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderCouponById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOrderCouponByIds(String[] ids);
}
