package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TOrderUseLogMapper;
import co.tton.qcloud.system.domain.TOrderUseLog;
import co.tton.qcloud.system.service.ITOrderUseLogService;

/**
 * 订单使用状况Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TOrderUseLogServiceImpl implements ITOrderUseLogService 
{
    @Autowired
    private TOrderUseLogMapper tOrderUseLogMapper;

    /**
     * 查询订单使用状况
     * 
     * @param id 订单使用状况ID
     * @return 订单使用状况
     */
    @Override
    public TOrderUseLog selectTOrderUseLogById(String id)
    {
        return tOrderUseLogMapper.selectTOrderUseLogById(id);
    }

    /**
     * 查询订单使用状况列表
     * 
     * @param tOrderUseLog 订单使用状况
     * @return 订单使用状况
     */
    @Override
    public List<TOrderUseLog> selectTOrderUseLogList(TOrderUseLog tOrderUseLog)
    {
        return tOrderUseLogMapper.selectTOrderUseLogList(tOrderUseLog);
    }

    /**
     * 新增订单使用状况
     * 
     * @param tOrderUseLog 订单使用状况
     * @return 结果
     */
    @Override
    public int insertTOrderUseLog(TOrderUseLog tOrderUseLog)
    {
        tOrderUseLog.setCreateTime(DateUtils.getNowDate());
        return tOrderUseLogMapper.insertTOrderUseLog(tOrderUseLog);
    }

    /**
     * 修改订单使用状况
     * 
     * @param tOrderUseLog 订单使用状况
     * @return 结果
     */
    @Override
    public int updateTOrderUseLog(TOrderUseLog tOrderUseLog)
    {
        tOrderUseLog.setUpdateTime(DateUtils.getNowDate());
        return tOrderUseLogMapper.updateTOrderUseLog(tOrderUseLog);
    }

    /**
     * 删除订单使用状况对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOrderUseLogByIds(String ids)
    {
        return tOrderUseLogMapper.deleteTOrderUseLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单使用状况信息
     * 
     * @param id 订单使用状况ID
     * @return 结果
     */
    public int deleteTOrderUseLogById(String id)
    {
        return tOrderUseLogMapper.deleteTOrderUseLogById(id);
    }
}
