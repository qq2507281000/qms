package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.domain.TShopCoursesStarting;
import co.tton.qcloud.system.domain.TShopCoursesStartingModel;

import java.util.List;

/**
 * 课程基本信息Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopCoursesStartingService
{
    //查询最近上新列表
    public List<TShopCoursesStartingModel> selectTShopCoursesStartingList(TShopCoursesStartingModel tShopCoursesStartingModel);

    //新增保存最近上新
    public int insertTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting);

    //查询最近上新
    public TShopCoursesStarting selectTShopCoursesStartingById(String id);

    //修改保存最近上新
    public int updateTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting);

    //删除最近上新
    public int deleteTShopCoursesStartingByIds(String ids);
}
