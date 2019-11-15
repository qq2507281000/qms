package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.domain.TShopRecommend;
import co.tton.qcloud.system.domain.TShopRecommendModel;
import co.tton.qcloud.system.model.ShopCoursesListModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推荐商家Mapper接口
 * 
 * @author suiwb
 * @date 2019-11-15
 */
public interface TShopRecommendMapper
{

    //查询推荐商家列表
    public List<TShopRecommendModel> selectTShopRecommendList(TShopRecommendModel tShopRecommendModel);

    //新增推荐商家
    public int insertTShopRecommend(TShopRecommend tShopRecommend);

    //查询推荐商家
    public TShopRecommend selectTShopRecommendById(String id);

    //修改保存推荐商家
    public int updateTShopRecommend(TShopRecommend tShopRecommend);

    //删除推荐商家
    public int deleteTShopRecommendByIds(String[] ids);


}
