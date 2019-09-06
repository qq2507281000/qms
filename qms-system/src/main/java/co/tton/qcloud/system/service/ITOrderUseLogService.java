package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TOrderUseLog;
import java.util.List;

/**
 * 订单使用状况Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITOrderUseLogService 
{
    /**
     * 查询订单使用状况
     * 
     * @param id 订单使用状况ID
     * @return 订单使用状况
     */
    public TOrderUseLog selectTOrderUseLogById(String id);

    /**
     * 查询订单使用状况列表
     * 
     * @param tOrderUseLog 订单使用状况
     * @return 订单使用状况集合
     */
    public List<TOrderUseLog> selectTOrderUseLogList(TOrderUseLog tOrderUseLog);

    /**
     * 新增订单使用状况
     * 
     * @param tOrderUseLog 订单使用状况
     * @return 结果
     */
    public int insertTOrderUseLog(TOrderUseLog tOrderUseLog);

    /**
     * 修改订单使用状况
     * 
     * @param tOrderUseLog 订单使用状况
     * @return 结果
     */
    public int updateTOrderUseLog(TOrderUseLog tOrderUseLog);

    /**
     * 批量删除订单使用状况
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOrderUseLogByIds(String ids);

    /**
     * 删除订单使用状况信息
     * 
     * @param id 订单使用状况ID
     * @return 结果
     */
    public int deleteTOrderUseLogById(String id);
}
