package co.tton.qcloud.system.service.impl;

import java.util.List;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.service.ITShopCoursesService;

/**
 * 课程基本信息Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesServiceImpl implements ITShopCoursesService 
{
    @Autowired
    private TShopCoursesMapper tShopCoursesMapper;

    /**
     * 查询课程基本信息
     * 
     * @param id 课程基本信息ID
     * @return 课程基本信息
     */
    @Override
    public TShopCourses selectTShopCoursesById(String id)
    {
        return tShopCoursesMapper.selectTShopCoursesById(id);
    }

    /**
     * 查询课程基本信息列表
     * 
     * @param tShopCourses 课程基本信息
     * @return 课程基本信息
     */
    @Override
    public List<TShopCourses> selectTShopCoursesList(TShopCourses tShopCourses)
    {
        return tShopCoursesMapper.selectTShopCoursesList(tShopCourses);
    }

    /**
     * 新增课程基本信息
     * 
     * @param tShopCourses 课程基本信息
     * @return 结果
     */
    @Override
    public int insertTShopCourses(TShopCourses tShopCourses)
    {
        tShopCourses.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesMapper.insertTShopCourses(tShopCourses);
    }

    /**
     * 修改课程基本信息
     * 
     * @param tShopCourses 课程基本信息
     * @return 结果
     */
    @Override
    public int updateTShopCourses(TShopCourses tShopCourses)
    {
        tShopCourses.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesMapper.updateTShopCourses(tShopCourses);
    }

    /**
     * 删除课程基本信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTShopCoursesByIds(String ids)
    {
        return tShopCoursesMapper.deleteTShopCoursesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程基本信息信息
     * 
     * @param id 课程基本信息ID
     * @return 结果
     */
    public int deleteTShopCoursesById(String id)
    {
        return tShopCoursesMapper.deleteTShopCoursesById(id);
    }
}
