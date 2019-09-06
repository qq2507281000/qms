package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TOrderPayment;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TOrderPaymentMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TOrderPayment selectTOrderPaymentById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tOrderPayment 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TOrderPayment> selectTOrderPaymentList(TOrderPayment tOrderPayment);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tOrderPayment 【请填写功能名称】
     * @return 结果
     */
    public int insertTOrderPayment(TOrderPayment tOrderPayment);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tOrderPayment 【请填写功能名称】
     * @return 结果
     */
    public int updateTOrderPayment(TOrderPayment tOrderPayment);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTOrderPaymentById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOrderPaymentByIds(String[] ids);
}
