package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TCategory;
import java.util.List;

/**
 * 分类基础Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITCategoryService 
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
     * 批量删除分类基础
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCategoryByIds(String ids);

    /**
     * 删除分类基础信息
     * 
     * @param id 分类基础ID
     * @return 结果
     */
    public int deleteTCategoryById(String id);

    /**
     * 获取顶级分类
     *
     * @param
     * @return 结果
     */
    List<TCategory> getTopCatgory();

    /**
     * 获取子级分类
     *
     * @param
     * @return 结果
     */
    List<TCategory> getAllCategory(String str);

    /**
     * 根据顶级分类搜索子级分类
     *
     * @param parentId
     * @return 结果
     */
    List<TCategory> getSubCategory(String parentId);

    /**
     * 搜索框查询
     *
     * @param searchKey
     * @return 结果
     */
    List<TCategory> searchCategory(String searchKey);
}
