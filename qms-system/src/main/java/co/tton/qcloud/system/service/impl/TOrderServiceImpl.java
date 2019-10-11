package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.system.domain.TOrderDetailModel;
import co.tton.qcloud.system.domain.TOrderModel;
import co.tton.qcloud.system.domain.WxOrderDetail;
import co.tton.qcloud.system.service.ITOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TOrderMapper;
import co.tton.qcloud.system.domain.TOrder;
import co.tton.qcloud.system.service.ITOrderService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TOrderServiceImpl implements ITOrderService 
{
    @Autowired
    private TOrderMapper tOrderMapper;

    @Autowired
    private ITOrderDetailService detailService;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TOrder selectTOrderById(String id)
    {
        return tOrderMapper.selectTOrderById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrder 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TOrder> selectTOrderList(TOrder tOrder)
    {
        return tOrderMapper.selectTOrderList(tOrder);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrder 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTOrder(TOrder tOrder)
    {
        tOrder.setCreateTime(DateUtils.getNowDate());
        return tOrderMapper.insertTOrder(tOrder);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrder 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTOrder(TOrder tOrder)
    {
        tOrder.setUpdateTime(DateUtils.getNowDate());
        return tOrderMapper.updateTOrder(tOrder);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOrderByIds(String ids)
    {
        return tOrderMapper.deleteTOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderById(String id)
    {
        return tOrderMapper.deleteTOrderById(id);
    }

    /**
     * 查询所有订单列表
     *
     * @param
     * @return 结果
     */
    @Override
    public List<TOrder> getOrderList(TOrder tOrder) {
        return tOrderMapper.getOrderList(tOrder);
    }

    /**
     * 微信小程序 查询所有订单详情
     *
     * @param orderId
     * @return 结果
     */
    @Override
    public WxOrderDetail getOrderDetail(String orderId) {
        return tOrderMapper.getOrderDetail(orderId);
    }

    /**
     * 微信小程序 根据订单状态获取订单数量
     *
     * @param tOrder
     * @return 结果
     */
    @Override
    public TOrderModel getCountOrder(TOrderModel tOrder) {
        return tOrderMapper.getCountOrder(tOrder);
    }


    /***
     * 获取完整订单信息
     * @param id 订单Id
     * @return
     */
    @Override
    public TOrderModel selectFullOrderById(String id) {
        TOrderModel orderModel = tOrderMapper.selectFullOrderById(id);
        List<TOrderDetailModel> details = detailService.selectTOrderDetailModelList(id);
        orderModel.setDetails(details);
        return orderModel;
    }
}
