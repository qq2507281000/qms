package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TOrderDetailMapper;
import co.tton.qcloud.system.domain.TOrderDetail;
import co.tton.qcloud.system.service.ITOrderDetailService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TOrderDetailServiceImpl implements ITOrderDetailService 
{
    @Autowired
    private TOrderDetailMapper tOrderDetailMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TOrderDetail selectTOrderDetailById(String id)
    {
        return tOrderDetailMapper.selectTOrderDetailById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrderDetail 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TOrderDetail> selectTOrderDetailList(TOrderDetail tOrderDetail)
    {
        return tOrderDetailMapper.selectTOrderDetailList(tOrderDetail);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrderDetail 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTOrderDetail(TOrderDetail tOrderDetail)
    {
        tOrderDetail.setCreateTime(DateUtils.getNowDate());
        return tOrderDetailMapper.insertTOrderDetail(tOrderDetail);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrderDetail 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTOrderDetail(TOrderDetail tOrderDetail)
    {
        tOrderDetail.setUpdateTime(DateUtils.getNowDate());
        return tOrderDetailMapper.updateTOrderDetail(tOrderDetail);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOrderDetailByIds(String ids)
    {
        return tOrderDetailMapper.deleteTOrderDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderDetailById(String id)
    {
        return tOrderDetailMapper.deleteTOrderDetailById(id);
    }
}
