package co.tton.qcloud.system.mapper;


import co.tton.qcloud.system.domain.TShopCoursesReplaced;
import co.tton.qcloud.system.domain.TShopCoursesReplacedModel;
import co.tton.qcloud.system.domain.TShopCoursesStarting;
import co.tton.qcloud.system.domain.TShopCoursesStartingModel;

import java.util.List;

/**
 * 开机推荐Mapper接口
 * 
 * @author suiwb
 * @date 2019-11-15
 */
public interface TShopCoursesStartingMapper
{

    //查询开机推荐列表
    public List<TShopCoursesStartingModel> selectTShopCoursesStartingList(TShopCoursesStartingModel tShopCoursesStartingModel);

    //新增开机推荐
    public int insertTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting);

    //查询开机推荐
    public TShopCoursesStarting selectTShopCoursesStartingById(String id);

    //修改保存开机推荐
    public int updateTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting);

    //删除开机推荐
    public int deleteTShopCoursesStartingByIds(String[] ids);


}
