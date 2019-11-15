package co.tton.qcloud.system.service.impl;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.mapper.TShopCoursesStartingMapper;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.system.service.ITShopCoursesStartingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程基本信息Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesStartingServiceImpl implements ITShopCoursesStartingService {
    @Autowired
    private TShopCoursesStartingMapper tShopCoursesStartingMapper;


    /**
     * 查询推荐课程列表
     * @param tShopCoursesStartingModel
     * @return
     */
    @Override
    public List<TShopCoursesStartingModel> selectTShopCoursesStartingList(TShopCoursesStartingModel tShopCoursesStartingModel) {
        tShopCoursesStartingModel.setFlag(Constants.DATA_NORMAL);
        return tShopCoursesStartingMapper.selectTShopCoursesStartingList(tShopCoursesStartingModel);
    }

    /**
     * 新增最近上新
     *
     * @param tShopCoursesStarting 最近上新
     * @return 结果
     */
    @Override
    public int insertTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting)
    {
        tShopCoursesStarting.setId(StringUtils.genericId());
        tShopCoursesStarting.setFlag(Constants.DATA_NORMAL);
        tShopCoursesStarting.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesStartingMapper.insertTShopCoursesStarting(tShopCoursesStarting);
    }

    /**
     * 查询最近上新
     * @param id
     * @return
     */
    @Override
    public TShopCoursesStarting selectTShopCoursesStartingById(String id) {
        return tShopCoursesStartingMapper.selectTShopCoursesStartingById(id);
    }

    /**
     * 修改保存最近上新
     * @param tShopCoursesStarting
     * @return
     */
    @Override
    public int updateTShopCoursesStarting(TShopCoursesStarting tShopCoursesStarting) {
        tShopCoursesStarting.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesStartingMapper.updateTShopCoursesStarting(tShopCoursesStarting);
    }

    /**
     * 删除最近上新
     * @param ids
     * @return
     */
    @Override
    public int deleteTShopCoursesStartingByIds(String ids) {
        return tShopCoursesStartingMapper.deleteTShopCoursesStartingByIds(Convert.toStrArray(ids));
    }
}