package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TOrderDetail;
import co.tton.qcloud.system.domain.TOrderDetailModel;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITOrderDetailService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TOrderDetail selectTOrderDetailById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrderDetail 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TOrderDetail> selectTOrderDetailList(TOrderDetail tOrderDetail);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrderDetail 【请填写功能名称】
     * @return 结果
     */
    public int insertTOrderDetail(TOrderDetail tOrderDetail);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrderDetail 【请填写功能名称】
     * @return 结果
     */
    public int updateTOrderDetail(TOrderDetail tOrderDetail);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOrderDetailByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderDetailById(String id);


    /***
     * 获取订单详情列表
     * @param orderId 订单Id
     * @return
     */
    public List<TOrderDetailModel> selectTOrderDetailModelList(String orderId);
}
