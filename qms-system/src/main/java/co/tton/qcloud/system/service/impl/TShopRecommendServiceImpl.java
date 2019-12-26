package co.tton.qcloud.system.service.impl;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.mapper.TShopRecommendMapper;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.system.service.ITShopRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推荐商家Service业务层处理
 * 
 * @author suiwb
 * @date 2019-11-15
 */
@Service
public class TShopRecommendServiceImpl implements ITShopRecommendService {
    @Autowired
    private TShopRecommendMapper tShopRecommendMapper;


    /**
     * 查询推荐商家列表
     * @param tShopRecommendModel
     * @return
     */
    @Override
    public List<TShopRecommendModel> selectTShopRecommendList(TShopRecommendModel tShopRecommendModel) {
        tShopRecommendModel.setFlag(Constants.DATA_NORMAL);
        return tShopRecommendMapper.selectTShopRecommendList(tShopRecommendModel);
    }

    /**
     * 新增推荐商家
     *
     * @param tShopRecommend 推荐商家
     * @return 结果
     */
    @Override
    public int insertTShopRecommend(TShopRecommend tShopRecommend)
    {
        tShopRecommend.setId(StringUtils.genericId());
        tShopRecommend.setFlag(Constants.DATA_NORMAL);
        tShopRecommend.setCreateTime(DateUtils.getNowDate());
        return tShopRecommendMapper.insertTShopRecommend(tShopRecommend);
    }

    /**
     * 查询推荐商家
     * @param id
     * @return
     */
    @Override
    public TShopRecommend selectTShopRecommendById(String id) {
        return tShopRecommendMapper.selectTShopRecommendById(id);
    }

    /**
     * 修改保存推荐商家
     * @param tShopRecommend
     * @return
     */
    @Override
    public int updateTShopRecommend(TShopRecommend tShopRecommend) {
        tShopRecommend.setUpdateTime(DateUtils.getNowDate());
        return tShopRecommendMapper.updateTShopRecommend(tShopRecommend);
    }

    /**
     * 删除推荐商家
     * @param ids
     * @return
     */
    @Override
    public int deleteTShopRecommendByIds(String ids) {
        return tShopRecommendMapper.deleteTShopRecommendByIds(Convert.toStrArray(ids));
    }
}