package co.tton.qcloud.system.service.impl;

import java.util.List;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TCouponReceiveLogMapper;
import co.tton.qcloud.system.domain.TCouponReceiveLog;
import co.tton.qcloud.system.service.ITCouponReceiveLogService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TCouponReceiveLogServiceImpl implements ITCouponReceiveLogService 
{
    @Autowired
    private TCouponReceiveLogMapper tCouponReceiveLogMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TCouponReceiveLog selectTCouponReceiveLogById(String id)
    {
        return tCouponReceiveLogMapper.selectTCouponReceiveLogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tCouponReceiveLog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TCouponReceiveLog> selectTCouponReceiveLogList(TCouponReceiveLog tCouponReceiveLog)
    {
        return tCouponReceiveLogMapper.selectTCouponReceiveLogList(tCouponReceiveLog);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tCouponReceiveLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTCouponReceiveLog(TCouponReceiveLog tCouponReceiveLog)
    {
        tCouponReceiveLog.setCreateTime(DateUtils.getNowDate());
        return tCouponReceiveLogMapper.insertTCouponReceiveLog(tCouponReceiveLog);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tCouponReceiveLog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTCouponReceiveLog(TCouponReceiveLog tCouponReceiveLog)
    {
        tCouponReceiveLog.setUpdateTime(DateUtils.getNowDate());
        return tCouponReceiveLogMapper.updateTCouponReceiveLog(tCouponReceiveLog);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTCouponReceiveLogByIds(String ids)
    {
        return tCouponReceiveLogMapper.deleteTCouponReceiveLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTCouponReceiveLogById(String id)
    {
        return tCouponReceiveLogMapper.deleteTCouponReceiveLogById(id);
    }
}
