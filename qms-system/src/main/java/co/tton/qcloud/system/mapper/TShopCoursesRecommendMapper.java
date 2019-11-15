package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TShopCoursesRecommend;
import co.tton.qcloud.system.domain.TShopCoursesRecommendModel;
import co.tton.qcloud.system.domain.TShopRecommend;
import co.tton.qcloud.system.domain.TShopRecommendModel;

import java.util.List;

/**
 * 推荐课程Mapper接口
 * 
 * @author suiwb
 * @date 2019-11-15
 */
public interface TShopCoursesRecommendMapper
{

    //查询推荐课程列表
    public List<TShopCoursesRecommendModel> selectTShopCoursesRecommendList(TShopCoursesRecommendModel tShopCoursesRecommendModel);

    //新增推荐课程
    public int insertTShopCoursesRecommend(TShopCoursesRecommend tShopCoursesRecommend);

    //查询推荐课程
    public TShopCoursesRecommend selectTShopCoursesRecommendById(String id);

    //修改保存推荐课程
    public int updateTShopCoursesRecommend(TShopCoursesRecommend tShopCoursesRecommend);

    //删除推荐课程
    public int deleteTShopCoursesRecommendByIds(String[] ids);


}
