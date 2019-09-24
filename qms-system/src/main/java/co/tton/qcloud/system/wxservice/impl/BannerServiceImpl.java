package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TBanner;
import co.tton.qcloud.system.mapper.TBannerMapper;
import co.tton.qcloud.system.wxservice.ITBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页滚动广告Service业务层处理
 *
 * @author qcloud
 * @date 2019-09-23
 */
@Service
public class BannerServiceImpl implements ITBannerService {

  @Resource
  private TBannerMapper tBannerMapper;

  @Override
  public List<TBanner> getBanner() {
    return tBannerMapper.getBanner();
  }
}
