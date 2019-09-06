package co.tton.qcloud.system.service.impl;

import java.util.List;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TCategoryMapper;
import co.tton.qcloud.system.domain.TCategory;
import co.tton.qcloud.system.service.ITCategoryService;

/**
 * 分类基础Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TCategoryServiceImpl implements ITCategoryService 
{
    @Autowired
    private TCategoryMapper tCategoryMapper;

    /**
     * 查询分类基础
     * 
     * @param id 分类基础ID
     * @return 分类基础
     */
    @Override
    public TCategory selectTCategoryById(String id)
    {
        return tCategoryMapper.selectTCategoryById(id);
    }

    /**
     * 查询分类基础列表
     * 
     * @param tCategory 分类基础
     * @return 分类基础
     */
    @Override
    public List<TCategory> selectTCategoryList(TCategory tCategory)
    {
        return tCategoryMapper.selectTCategoryList(tCategory);
    }

    /**
     * 新增分类基础
     * 
     * @param tCategory 分类基础
     * @return 结果
     */
    @Override
    public int insertTCategory(TCategory tCategory)
    {
        tCategory.setCreateTime(DateUtils.getNowDate());
        return tCategoryMapper.insertTCategory(tCategory);
    }

    /**
     * 修改分类基础
     * 
     * @param tCategory 分类基础
     * @return 结果
     */
    @Override
    public int updateTCategory(TCategory tCategory)
    {
        tCategory.setUpdateTime(DateUtils.getNowDate());
        return tCategoryMapper.updateTCategory(tCategory);
    }

    /**
     * 删除分类基础对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTCategoryByIds(String ids)
    {
        return tCategoryMapper.deleteTCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除分类基础信息
     * 
     * @param id 分类基础ID
     * @return 结果
     */
    public int deleteTCategoryById(String id)
    {
        return tCategoryMapper.deleteTCategoryById(id);
    }
}
