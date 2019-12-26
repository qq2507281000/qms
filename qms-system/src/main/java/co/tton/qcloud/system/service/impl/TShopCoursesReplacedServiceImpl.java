package co.tton.qcloud.system.service.impl;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.mapper.TShopCoursesMapper;
import co.tton.qcloud.system.mapper.TShopCoursesReplacedMapper;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesReplacedService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 最近上新Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesReplacedServiceImpl implements ITShopCoursesReplacedService {
    @Autowired
    private TShopCoursesReplacedMapper tShopCoursesReplacedMapper;


    /**
     * 查询推荐课程列表
     * @param tShopCoursesReplacedModel
     * @return
     */
    @Override
    public List<TShopCoursesReplacedModel> selectTShopCoursesReplacedList(TShopCoursesReplacedModel tShopCoursesReplacedModel) {
        tShopCoursesReplacedModel.setFlag(Constants.DATA_NORMAL);
        return tShopCoursesReplacedMapper.selectTShopCoursesReplacedList(tShopCoursesReplacedModel);
    }

    /**
     * 新增最近上新
     *
     * @param tShopCoursesReplaced 最近上新
     * @return 结果
     */
    @Override
    public int insertTShopCoursesReplaced(TShopCoursesReplaced tShopCoursesReplaced)
    {
        tShopCoursesReplaced.setId(StringUtils.genericId());
        tShopCoursesReplaced.setFlag(Constants.DATA_NORMAL);
        tShopCoursesReplaced.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesReplacedMapper.insertTShopCoursesReplaced(tShopCoursesReplaced);
    }

    /**
     * 查询最近上新
     * @param id
     * @return
     */
    @Override
    public TShopCoursesReplaced selectTShopCoursesReplacedById(String id) {
        return tShopCoursesReplacedMapper.selectTShopCoursesReplacedById(id);
    }

    /**
     * 修改保存最近上新
     * @param tShopCoursesReplaced
     * @return
     */
    @Override
    public int updateTShopCoursesReplaced(TShopCoursesReplaced tShopCoursesReplaced) {
        tShopCoursesReplaced.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesReplacedMapper.updateTShopCoursesReplaced(tShopCoursesReplaced);
    }

    /**
     * 删除最近上新
     * @param ids
     * @return
     */
    @Override
    public int deleteTShopCoursesReplacedByIds(String ids) {
        return tShopCoursesReplacedMapper.deleteTShopCoursesReplacedByIds(Convert.toStrArray(ids));
    }
}