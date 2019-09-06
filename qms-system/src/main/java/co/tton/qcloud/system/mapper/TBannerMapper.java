package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TBanner;
import java.util.List;

/**
 * 首页滚动广告Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TBannerMapper 
{
    /**
     * 查询首页滚动广告
     * 
     * @param id 首页滚动广告ID
     * @return 首页滚动广告
     */
    public TBanner selectTBannerById(String id);

    /**
     * 查询首页滚动广告列表
     * 
     * @param tBanner 首页滚动广告
     * @return 首页滚动广告集合
     */
    public List<TBanner> selectTBannerList(TBanner tBanner);

    /**
     * 新增首页滚动广告
     * 
     * @param tBanner 首页滚动广告
     * @return 结果
     */
    public int insertTBanner(TBanner tBanner);

    /**
     * 修改首页滚动广告
     * 
     * @param tBanner 首页滚动广告
     * @return 结果
     */
    public int updateTBanner(TBanner tBanner);

    /**
     * 删除首页滚动广告
     * 
     * @param id 首页滚动广告ID
     * @return 结果
     */
    public int deleteTBannerById(String id);

    /**
     * 批量删除首页滚动广告
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTBannerByIds(String[] ids);
}
