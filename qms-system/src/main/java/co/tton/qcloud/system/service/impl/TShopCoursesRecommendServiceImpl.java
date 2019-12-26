package co.tton.qcloud.system.service.impl;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.mapper.TShopCoursesRecommendMapper;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesRecommendService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推荐课程Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesRecommendServiceImpl implements ITShopCoursesRecommendService {
    @Autowired
    private TShopCoursesRecommendMapper tShopCoursesRecommendMapper;


    /**
     * 查询推荐课程列表
     * @param tShopCoursesRecommendModel
     * @return
     */
    @Override
    public List<TShopCoursesRecommendModel> selectTShopCoursesRecommendList(TShopCoursesRecommendModel tShopCoursesRecommendModel) {
        tShopCoursesRecommendModel.setFlag(Constants.DATA_NORMAL);
        return tShopCoursesRecommendMapper.selectTShopCoursesRecommendList(tShopCoursesRecommendModel);
    }

    /**
     * 新增推荐课程
     *
     * @param tShopCoursesRecommend 推荐课程
     * @return 结果
     */
    @Override
    public int insertTShopCoursesRecommend(TShopCoursesRecommend tShopCoursesRecommend)
    {
        tShopCoursesRecommend.setId(StringUtils.genericId());
        tShopCoursesRecommend.setFlag(Constants.DATA_NORMAL);
        tShopCoursesRecommend.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesRecommendMapper.insertTShopCoursesRecommend(tShopCoursesRecommend);
    }

    /**
     * 查询推荐课程
     * @param id
     * @return
     */
    @Override
    public TShopCoursesRecommend selectTShopCoursesRecommendById(String id) {
        return tShopCoursesRecommendMapper.selectTShopCoursesRecommendById(id);
    }

    /**
     * 修改保存推荐课程
     * @param tShopCoursesRecommend
     * @return
     */
    @Override
    public int updateTShopCoursesRecommend(TShopCoursesRecommend tShopCoursesRecommend) {
        tShopCoursesRecommend.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesRecommendMapper.updateTShopCoursesRecommend(tShopCoursesRecommend);
    }

    /**
     * 删除推荐课程
     * @param ids
     * @return
     */
    @Override
    public int deleteTShopCoursesRecommendByIds(String ids) {
        return tShopCoursesRecommendMapper.deleteTShopCoursesRecommendByIds(Convert.toStrArray(ids));
    }
}