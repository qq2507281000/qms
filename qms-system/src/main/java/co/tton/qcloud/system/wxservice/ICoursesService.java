package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TShopCoursesModel;

import java.util.List;

public interface ICoursesService {


    TShopCoursesModel getCoursesDetail(String id);

    List<TShopCoursesModel> getSuggestCourses(TShopCoursesModel tShopCoursesModel);

    /**
     * 小程序获取某商家某分类下课程接口
     *
     * @param  tShopCoursesModel 根据categoryId查询
     * @return 结果
     */
    List<TShopCoursesModel> getShopCategoryCourses(TShopCoursesModel tShopCoursesModel);

    /**
     * 获取商家所有课程分类接口
     *
     * @param  tShopCoursesModel 查询
     * @return 结果
     */
    List<TShopCoursesModel> getAllCoursesCategory(TShopCoursesModel tShopCoursesModel);

}
