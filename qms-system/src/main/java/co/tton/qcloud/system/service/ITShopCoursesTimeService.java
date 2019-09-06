package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShopCoursesTime;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopCoursesTimeService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TShopCoursesTime selectTShopCoursesTimeById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tShopCoursesTime 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TShopCoursesTime> selectTShopCoursesTimeList(TShopCoursesTime tShopCoursesTime);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tShopCoursesTime 【请填写功能名称】
     * @return 结果
     */
    public int insertTShopCoursesTime(TShopCoursesTime tShopCoursesTime);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tShopCoursesTime 【请填写功能名称】
     * @return 结果
     */
    public int updateTShopCoursesTime(TShopCoursesTime tShopCoursesTime);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTShopCoursesTimeByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTShopCoursesTimeById(String id);
}
