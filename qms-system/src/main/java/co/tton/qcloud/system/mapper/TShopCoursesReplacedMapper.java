package co.tton.qcloud.system.mapper;


import co.tton.qcloud.system.domain.TShopCoursesReplaced;
import co.tton.qcloud.system.domain.TShopCoursesReplacedModel;

import java.util.List;

/**
 * 最近上新Mapper接口
 * 
 * @author suiwb
 * @date 2019-11-15
 */
public interface TShopCoursesReplacedMapper
{

    //查询最近上新列表
    public List<TShopCoursesReplacedModel> selectTShopCoursesReplacedList(TShopCoursesReplacedModel tShopCoursesReplacedModel);

    //新增最近上新
    public int insertTShopCoursesReplaced(TShopCoursesReplaced tShopCoursesReplaced);

    //查询最近上新
    public TShopCoursesReplaced selectTShopCoursesReplacedById(String id);

    //修改保存最近上新
    public int updateTShopCoursesReplaced(TShopCoursesReplaced tShopCoursesReplaced);

    //删除最近上新
    public int deleteTShopCoursesReplacedByIds(String[] ids);


}
