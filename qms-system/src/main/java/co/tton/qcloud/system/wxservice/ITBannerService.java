package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TBanner;

import java.util.List;

/**
 * 首页滚动广告Service接口
 *
 * @author qcloud
 * @date 2019-09-23
 */
public interface ITBannerService {

  /**
   * 查询首页滚动广告列表
   *
   * @param
   * @return 首页滚动广告集合
   */
  List<TBanner> getBanner();
}
