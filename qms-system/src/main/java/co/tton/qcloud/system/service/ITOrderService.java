package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TOrder;
import co.tton.qcloud.system.domain.TOrderModel;
import co.tton.qcloud.system.domain.WxOrderDetail;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITOrderService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TOrder selectTOrderById(String id);

    /***
     * 根据订单编号获取订单详情
     * @param no
     * @return
     */
    public TOrder selectTOrderByNo(String no);

    /***
     * 获取完整订单信息
     * @param id 订单Id
     * @return
     */
    public TOrderModel selectFullOrderById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrder 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TOrder> selectTOrderList(TOrder tOrder);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrder 【请填写功能名称】
     * @return 结果
     */
    public int insertTOrder(TOrder tOrder);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrder 【请填写功能名称】
     * @return 结果
     */
    public int updateTOrder(TOrder tOrder);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOrderByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderById(String id);

    /**
     * 查询所有订单列表
     *
     * @param
     * @return 结果
     */
    List<TOrder> getOrderList(TOrder tOrder);

    /**
     * 微信小程序 查询所有订单详情
     *
     * @param orderId
     * @return 结果
     */
    WxOrderDetail getOrderDetail(String orderId);

    /**
     * 微信小程序 根据订单状态获取订单数量
     *
     * @param tOrder
     * @return 结果
     */
    TOrderModel getCountOrder(TOrderModel tOrder);

    /**
     * 微信小程序 课程下单时选择的类别
     *
     * @param tOrder
     * @return 结果
     */
    List<TOrderModel> getBillStatusCourses(TOrder tOrder);
}
