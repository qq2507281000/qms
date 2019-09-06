package co.tton.qcloud.system.service.impl;

import java.util.List;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TCouponUseLogMapper;
import co.tton.qcloud.system.domain.TCouponUseLog;
import co.tton.qcloud.system.service.ITCouponUseLogService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TCouponUseLogServiceImpl implements ITCouponUseLogService 
{
    @Autowired
    private TCouponUseLogMapper tCouponUseLogMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TCouponUseLog selectTCouponUseLogById(String id)
    {
        return tCouponUseLogMapper.selectTCouponUseLogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tCouponUseLog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TCouponUseLog> selectTCouponUseLogList(TCouponUseLog tCouponUseLog)
    {
        return tCouponUseLogMapper.selectTCouponUseLogList(tCouponUseLog);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tCouponUseLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTCouponUseLog(TCouponUseLog tCouponUseLog)
    {
        tCouponUseLog.setCreateTime(DateUtils.getNowDate());
        return tCouponUseLogMapper.insertTCouponUseLog(tCouponUseLog);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tCouponUseLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTCouponUseLog(TCouponUseLog tCouponUseLog)
    {
        tCouponUseLog.setUpdateTime(DateUtils.getNowDate());
        return tCouponUseLogMapper.updateTCouponUseLog(tCouponUseLog);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTCouponUseLogByIds(String ids)
    {
        return tCouponUseLogMapper.deleteTCouponUseLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTCouponUseLogById(String id)
    {
        return tCouponUseLogMapper.deleteTCouponUseLogById(id);
    }
}
