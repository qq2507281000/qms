package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TCategory;
import java.util.List;

/**
 * 分类基础Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TCategoryMapper 
{
    /**
     * 查询分类基础
     * 
     * @param id 分类基础ID
     * @return 分类基础
     */
    public TCategory selectTCategoryById(String id);

    /**
     * 查询分类基础列表
     * 
     * @param tCategory 分类基础
     * @return 分类基础集合
     */
    public List<TCategory> selectTCategoryList(TCategory tCategory);

    /**
     * 新增分类基础
     * 
     * @param tCategory 分类基础
     * @return 结果
     */
    public int insertTCategory(TCategory tCategory);

    /**
     * 修改分类基础
     * 
     * @param tCategory 分类基础
     * @return 结果
     */
    public int updateTCategory(TCategory tCategory);

    /**
     * 删除分类基础
     * 
     * @param id 分类基础ID
     * @return 结果
     */
    public int deleteTCategoryById(String id);

    /**
     * 批量删除分类基础
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCategoryByIds(String[] ids);
}
