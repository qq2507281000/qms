package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;

import java.util.List;

/**
 * 课程基本信息Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TShopCoursesMapper 
{
    /**
     * 查询课程基本信息
     * 
     * @param id 课程基本信息ID
     * @return 课程基本信息
     */
    public TShopCourses selectTShopCoursesById(String id);

    /**
     * 查询课程基本信息
     *
     * @param id 课程基本信息ID
     * @return 课程基本信息
     */
    public TShopCourses selectTShopCoursesByShopId(String shopId);

    /**
     * 查询课程基本信息列表
     * 
     * @param tShopCourses 课程基本信息
     * @return 课程基本信息集合
     */
    public List<TShopCourses> selectTShopCoursesList(TShopCourses tShopCourses);

    /**
     * 新增课程基本信息
     * 
     * @param tShopCourses 课程基本信息
     * @return 结果
     */
    public int insertTShopCourses(TShopCourses tShopCourses);

    /**
     * 修改课程基本信息
     * 
     * @param tShopCourses 课程基本信息
     * @return 结果
     */
    public int updateTShopCourses(TShopCourses tShopCourses);

    /**
     * 删除课程基本信息
     * 
     * @param id 课程基本信息ID
     * @return 结果
     */
    public int deleteTShopCoursesById(String id);

    /**
     * 批量删除课程基本信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTShopCoursesByIds(String[] ids);

    /**
     * 小程序课程基本信息
     *
     * @param id 根据Id查询有关课程的详细信息
     * @return 结果
     */
    TShopCoursesModel getCoursesDetail(String id);

    /**
     * 小程序查询推荐课程
     *
     * @param categoryId 根据categoryId查询有关推荐课程
     * @return 结果
     */
    List<TShopCoursesModel> getSuggestCourses(String categoryId);

    List<TShopCoursesModel> getSuggestCoursesAll();
}
