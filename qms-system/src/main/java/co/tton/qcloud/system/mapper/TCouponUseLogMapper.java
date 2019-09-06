package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TCouponUseLog;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TCouponUseLogMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TCouponUseLog selectTCouponUseLogById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tCouponUseLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TCouponUseLog> selectTCouponUseLogList(TCouponUseLog tCouponUseLog);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tCouponUseLog 【请填写功能名称】
     * @return 结果
     */
    public int insertTCouponUseLog(TCouponUseLog tCouponUseLog);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tCouponUseLog 【请填写功能名称】
     * @return 结果
     */
    public int updateTCouponUseLog(TCouponUseLog tCouponUseLog);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTCouponUseLogById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCouponUseLogByIds(String[] ids);
}
