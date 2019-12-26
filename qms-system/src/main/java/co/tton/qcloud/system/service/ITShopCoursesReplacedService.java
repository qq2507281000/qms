package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.domain.TShopCoursesReplaced;
import co.tton.qcloud.system.domain.TShopCoursesReplacedModel;

import java.util.List;

/**
 * 最近上新Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopCoursesReplacedService
{
    //查询最近上新列表
    public List<TShopCoursesReplacedModel> selectTShopCoursesReplacedList(TShopCoursesReplacedModel tShopCoursesReplacedModel);

    //新增保存最近上新
    public int insertTShopCoursesReplaced(TShopCoursesReplaced tShopCoursesReplaced);

    //查询最近上新
    public TShopCoursesReplaced selectTShopCoursesReplacedById(String id);

    //修改保存最近上新
    public int updateTShopCoursesReplaced(TShopCoursesReplaced tShopCoursesReplaced);

    //删除最近上新
    public int deleteTShopCoursesReplacedByIds(String ids);
}
