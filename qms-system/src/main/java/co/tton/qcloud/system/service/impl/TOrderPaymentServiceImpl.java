package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TOrderPaymentMapper;
import co.tton.qcloud.system.domain.TOrderPayment;
import co.tton.qcloud.system.service.ITOrderPaymentService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TOrderPaymentServiceImpl implements ITOrderPaymentService 
{
    @Autowired
    private TOrderPaymentMapper tOrderPaymentMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TOrderPayment selectTOrderPaymentById(String id)
    {
        return tOrderPaymentMapper.selectTOrderPaymentById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrderPayment 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TOrderPayment> selectTOrderPaymentList(TOrderPayment tOrderPayment)
    {
        return tOrderPaymentMapper.selectTOrderPaymentList(tOrderPayment);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrderPayment 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTOrderPayment(TOrderPayment tOrderPayment)
    {
        tOrderPayment.setCreateTime(DateUtils.getNowDate());
        return tOrderPaymentMapper.insertTOrderPayment(tOrderPayment);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrderPayment 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTOrderPayment(TOrderPayment tOrderPayment)
    {
        tOrderPayment.setUpdateTime(DateUtils.getNowDate());
        return tOrderPaymentMapper.updateTOrderPayment(tOrderPayment);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOrderPaymentByIds(String ids)
    {
        return tOrderPaymentMapper.deleteTOrderPaymentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderPaymentById(String id)
    {
        return tOrderPaymentMapper.deleteTOrderPaymentById(id);
    }
}
