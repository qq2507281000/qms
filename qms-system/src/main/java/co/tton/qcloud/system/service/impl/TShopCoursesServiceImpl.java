package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.service.ITShopCoursesService;

/**
 * 课程基本信息Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesServiceImpl implements ITShopCoursesService {
    @Autowired
    private TShopCoursesMapper tShopCoursesMapper;
    @Autowired
    private ITShopCoursesImagesService tShopCoursesImagesService;
    @Autowired
    private ITOrderDetailService tOrderDetailService;

    /**
     * 查询课程基本信息
     *
     * @param id 课程基本信息ID
     * @return 课程基本信息
     */
    @Override
    public TShopCourses selectTShopCoursesById(String id) {
        return tShopCoursesMapper.selectTShopCoursesById(id);
    }

    /**
     * 查询课程基本信息
     *
     * @param id 课程基本信息SHOPID
     * @return 课程基本信息
     */
    @Override
    public TShopCourses selectTShopCoursesByShopId(String id) {
        return tShopCoursesMapper.selectTShopCoursesByShopId(id);
    }

    /**
     * 查询课程基本信息列表
     *
     * @param tShopCourses 课程基本信息
     * @return 课程基本信息
     */
    @Override
    public List<TShopCourses> selectTShopCoursesList(TShopCourses tShopCourses) {
        tShopCourses.setFlag(Constants.DATA_NORMAL);
        return tShopCoursesMapper.selectTShopCoursesList(tShopCourses);
    }

    /**
     * 新增课程基本信息
     *
     * @param tShopCourses 课程基本信息
     * @return 结果
     */
    @Override
    public int insertTShopCourses(TShopCourses tShopCourses) {
        tShopCourses.setId(StringUtils.genericId());
        tShopCourses.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesMapper.insertTShopCourses(tShopCourses);
    }

    /**
     * 修改课程基本信息
     *
     * @param tShopCourses 课程基本信息
     * @return 结果
     */
    @Override
    public int updateTShopCourses(TShopCourses tShopCourses) {
        tShopCourses.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesMapper.updateTShopCourses(tShopCourses);
    }

    /**
     * 删除课程基本信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTShopCoursesByIds(String ids) {
        return tShopCoursesMapper.deleteTShopCoursesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程基本信息信息
     *
     * @param id 课程基本信息ID
     * @return 结果
     */
    public int deleteTShopCoursesById(String id) {
        return tShopCoursesMapper.deleteTShopCoursesById(id);
    }

    /**
     * 模糊查询课程
     *
     * @param
     * @return 获取课程详情
     */
    @Override
    public List<TShopCoursesModel> getNameShopCourses(String name) {
        List<TShopCoursesModel> tShopCoursesList = tShopCoursesMapper.getNameShopCourses(name);
        if (StringUtils.isNotEmpty(tShopCoursesList)) {
            for (int i = 0; i < tShopCoursesList.size(); i++) {
                //月销
                if(StringUtils.isNotEmpty(tShopCoursesList.get(i).getId())){
                    String count = tOrderDetailService.getOrderMon(tShopCoursesList.get(i).getId());
                    //图片
                    List<TShopCoursesImages> images = tShopCoursesImagesService.getImagesByid(tShopCoursesList.get(i).getId());
                    if (StringUtils.isNotEmpty(images)&& images.size() == 0){
                        String[] imageUrls = new String[images.size()];
                        if(StringUtils.isNotEmpty(images.get(i).getImageUrl())){
                            for (int j = 0; j < images.size(); j++) {
                                imageUrls[i] = images.get(j).getImageUrl();
                            }
                            tShopCoursesList.get(i).setImages(imageUrls);
                        }
                    }
                    tShopCoursesList.get(i).setCount(count);
                }
            }
        }
        return tShopCoursesList;
    }
}