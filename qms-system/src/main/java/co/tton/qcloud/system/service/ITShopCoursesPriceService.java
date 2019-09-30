package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShopCoursesPrice;
import java.util.List;

/**
 * 课程价格Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopCoursesPriceService 
{
    /**
     * 查询课程价格
     * 
     * @param id 课程价格ID
     * @return 课程价格
     */
    public TShopCoursesPrice selectTShopCoursesPriceById(String id);

    /**
     * 查询课程价格列表
     * 
     * @param tShopCoursesPrice 课程价格
     * @return 课程价格集合
     */
    public List<TShopCoursesPrice> selectTShopCoursesPriceList(TShopCoursesPrice tShopCoursesPrice);

    /**
     * 新增课程价格
     * 
     * @param tShopCoursesPrice 课程价格
     * @return 结果
     */
    public int insertTShopCoursesPrice(TShopCoursesPrice tShopCoursesPrice);

    /**
     * 修改课程价格
     * 
     * @param tShopCoursesPrice 课程价格
     * @return 结果
     */
    public int updateTShopCoursesPrice(TShopCoursesPrice tShopCoursesPrice);

    /**
     * 批量删除课程价格
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTShopCoursesPriceByIds(String ids);

    /**
     * 删除课程价格信息
     * 
     * @param id 课程价格ID
     * @return 结果
     */
    public int deleteTShopCoursesPriceById(String id);

    List<TShopCoursesPrice> getCoursesPriceById(String coursesId);
}
