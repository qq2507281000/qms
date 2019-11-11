package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TOrderUseEvaluation;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.model.ShopCoursesListModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICoursesService {

    /**
     * 小程序课程基本信息
     *
     * @param id 根据Id查询有关课程的详细信息
     * @return 结果
     */
    TShopCoursesModel getCoursesDetail(String id);

    /**
     * 小程序查询推荐课程
     *
     * @param  tShopCoursesModel 根据categoryId查询有关推荐课程
     * @return 结果
     */
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

    /**
     * 首页弹窗
     *
     * @param  tShopCourses 查询
     * @return 结果
     */
    TShopCoursesModel getMaxSortKeyCourses(TShopCourses tShopCourses);

    /**
     * 获取课程评价
     *
     * @param  coursesId 查询
     * @return 结果
     */
    List<TOrderUseEvaluation> getCoursesCategory(String coursesId);

    /**
     * 小程序搜索框收藏课程
     *
     * @param  coursesName memberId 根据coursesName查询
     * @return 结果
     */
    List<TShopCoursesModel> getcollectionCourses(@Param("title") String coursesName, @Param("memberId") String memberId);

    List<ShopCoursesListModel> getLatestCourses(String location);
}
