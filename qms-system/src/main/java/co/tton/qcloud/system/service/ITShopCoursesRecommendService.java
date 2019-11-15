package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.domain.TShopCoursesRecommend;
import co.tton.qcloud.system.domain.TShopCoursesRecommendModel;

import java.util.List;

/**
 * 推荐课程Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopCoursesRecommendService
{

    //查询推荐课程列表
    public List<TShopCoursesRecommendModel> selectTShopCoursesRecommendList(TShopCoursesRecommendModel tShopCoursesRecommendModel);

    //新增保存推荐课程
    public int insertTShopCoursesRecommend(TShopCoursesRecommend tShopCoursesRecommend);

    //查询推荐课程
    public TShopCoursesRecommend selectTShopCoursesRecommendById(String id);

    //修改保存推荐课程
    public int updateTShopCoursesRecommend(TShopCoursesRecommend tShopCoursesRecommend);

    //删除推荐课程
    public int deleteTShopCoursesRecommendByIds(String ids);

}
