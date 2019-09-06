package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TCouponReceiveLog;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITCouponReceiveLogService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TCouponReceiveLog selectTCouponReceiveLogById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tCouponReceiveLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TCouponReceiveLog> selectTCouponReceiveLogList(TCouponReceiveLog tCouponReceiveLog);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tCouponReceiveLog 【请填写功能名称】
     * @return 结果
     */
    public int insertTCouponReceiveLog(TCouponReceiveLog tCouponReceiveLog);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tCouponReceiveLog 【请填写功能名称】
     * @return 结果
     */
    public int updateTCouponReceiveLog(TCouponReceiveLog tCouponReceiveLog);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCouponReceiveLogByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTCouponReceiveLogById(String id);
}
