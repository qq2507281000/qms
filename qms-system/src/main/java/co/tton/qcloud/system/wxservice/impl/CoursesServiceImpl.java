package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TOrderUseEvaluation;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.mapper.TOrderUseEvaluationMapper;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.wxservice.ICoursesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CoursesServiceImpl implements ICoursesService {

    @Resource
    private TShopCoursesMapper tShopCoursesMapper;
    @Resource
    private TOrderUseEvaluationMapper tOrderUseEvaluationMapper;

    @Override
    public TShopCoursesModel getCoursesDetail(String id) {
        return tShopCoursesMapper.getCoursesDetail(id);
    }

    @Override
    public List<TShopCoursesModel> getSuggestCourses(TShopCoursesModel tShopCoursesModel) {
        return tShopCoursesMapper.getSuggestCourses(tShopCoursesModel);
    }

    /**
     * 小程序获取某商家某分类下课程接口
     *
     * @param  tShopCoursesModel 查询
     * @return 结果
     */
    @Override
    public List<TShopCoursesModel> getShopCategoryCourses(TShopCoursesModel tShopCoursesModel) {
        return tShopCoursesMapper.getShopCategoryCourses(tShopCoursesModel);
    }

    /**
     * 获取商家所有课程分类接口
     *
     * @param  tShopCoursesModel 查询
     * @return 结果
     */
    @Override
    public List<TShopCoursesModel> getAllCoursesCategory(TShopCoursesModel tShopCoursesModel) {
        return tShopCoursesMapper.getAllCoursesCategory(tShopCoursesModel);
    }

    /**
     * 首页弹窗
     *
     * @param  tShopCourses 查询
     * @return 结果
     */
    @Override
    public TShopCoursesModel getMaxSortKeyCourses(TShopCourses tShopCourses) {
        return tShopCoursesMapper.getMaxSortKeyCourses(tShopCourses);
    }

    /**
     * 获取课程评价
     *
     * @param  coursesId 查询
     * @return 结果
     */
    @Override
    public List<TOrderUseEvaluation> getCoursesCategory(String coursesId) {
        return tOrderUseEvaluationMapper.getCoursesCategory(coursesId);
    }

    /**
     * 小程序搜索框收藏课程
     *
     * @param  coursesName 根据coursesName查询
     * @return 结果
     */
    @Override
    public List<TShopCoursesModel> getcollectionCourses(String coursesName) {
        return tShopCoursesMapper.getcollectionCourses(coursesName);
    }

}
