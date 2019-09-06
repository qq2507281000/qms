package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TCouponGrant;
import java.util.List;

/**
 * 优惠券生成参数库Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TCouponGrantMapper 
{
    /**
     * 查询优惠券生成参数库
     * 
     * @param id 优惠券生成参数库ID
     * @return 优惠券生成参数库
     */
    public TCouponGrant selectTCouponGrantById(String id);

    /**
     * 查询优惠券生成参数库列表
     * 
     * @param tCouponGrant 优惠券生成参数库
     * @return 优惠券生成参数库集合
     */
    public List<TCouponGrant> selectTCouponGrantList(TCouponGrant tCouponGrant);

    /**
     * 新增优惠券生成参数库
     * 
     * @param tCouponGrant 优惠券生成参数库
     * @return 结果
     */
    public int insertTCouponGrant(TCouponGrant tCouponGrant);

    /**
     * 修改优惠券生成参数库
     * 
     * @param tCouponGrant 优惠券生成参数库
     * @return 结果
     */
    public int updateTCouponGrant(TCouponGrant tCouponGrant);

    /**
     * 删除优惠券生成参数库
     * 
     * @param id 优惠券生成参数库ID
     * @return 结果
     */
    public int deleteTCouponGrantById(String id);

    /**
     * 批量删除优惠券生成参数库
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCouponGrantByIds(String[] ids);
}
