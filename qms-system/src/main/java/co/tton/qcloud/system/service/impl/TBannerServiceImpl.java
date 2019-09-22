package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TBannerMapper;
import co.tton.qcloud.system.domain.TBanner;
import co.tton.qcloud.system.service.ITBannerService;

/**
 * 首页滚动广告Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TBannerServiceImpl implements ITBannerService 
{
    @Autowired
    private TBannerMapper tBannerMapper;

    /**
     * 查询首页滚动广告
     * 
     * @param id 首页滚动广告ID
     * @return 首页滚动广告
     */
    @Override
    public TBanner selectTBannerById(String id)
    {
        return tBannerMapper.selectTBannerById(id);
    }

    /**
     * 查询首页滚动广告列表
     * 
     * @param tBanner 首页滚动广告
     * @return 首页滚动广告
     */
    @Override
    public List<TBanner> selectTBannerList(TBanner tBanner)
    {
        tBanner.setFlag(Constants.DATA_NORMAL);
        return tBannerMapper.selectTBannerList(tBanner);
    }

    /**
     * 新增首页滚动广告
     * 
     * @param tBanner 首页滚动广告
     * @return 结果
     */
    @Override
    public int insertTBanner(TBanner tBanner)
    {
        tBanner.setCreateTime(DateUtils.getNowDate());
        return tBannerMapper.insertTBanner(tBanner);
    }

    /**
     * 修改首页滚动广告
     * 
     * @param tBanner 首页滚动广告
     * @return 结果
     */
    @Override
    public int updateTBanner(TBanner tBanner)
    {
        tBanner.setUpdateTime(DateUtils.getNowDate());
        return tBannerMapper.updateTBanner(tBanner);
    }

    /**
     * 删除首页滚动广告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTBannerByIds(String ids)
    {
        return tBannerMapper.deleteTBannerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除首页滚动广告信息
     * 
     * @param id 首页滚动广告ID
     * @return 结果
     */
    public int deleteTBannerById(String id)
    {
        return tBannerMapper.deleteTBannerById(id);
    }
}
