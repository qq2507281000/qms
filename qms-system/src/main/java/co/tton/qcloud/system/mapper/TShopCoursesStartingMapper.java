package co.tton.qcloud.system.mapper;


import co.tton.qcloud.system.domain.TShopCoursesReplaced;
import co.tton.qcloud.system.domain.TShopCoursesReplacedModel;
import co.tton.qcloud.system.domain.TShopCoursesStarting;
import co.tton.qcloud.system.domain.TShopCoursesStartingModel;

import java.util.List;

/**
 * 最近上新Mapper接口
 * 
 * @author suiwb
 * @date 2019-11-15
 */
public interface TShopCoursesStartingMapper
{

    //查询最近上新列表
    public List<TShopCoursesStartingModel> selectTShopCoursesStartingList(TShopCoursesStartingModel tShopCoursesStartingModel);

    //新增最近上新
    public int insertTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting);

    //查询最近上新
    public TShopCoursesStarting selectTShopCoursesStartingById(String id);

    //修改保存最近上新
    public int updateTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting);

    //删除最近上新
    public int deleteTShopCoursesStartingByIds(String[] ids);


}
