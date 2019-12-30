package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TBanner;
import co.tton.qcloud.system.service.ISysDictDataService;
import co.tton.qcloud.system.wxservice.ITBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:16
 */

@Api(value = "小程序首页广告信息", tags = "小程序首页广告信息")
@RestController
@RequestMapping("/api/v1.0/banner")
public class BannerController extends BaseController {

  @Autowired
  private ITBannerService tBannerService;

  @Autowired
  private ISysDictDataService dictService;

  @ApiOperation("查询首页滚动广告")
//    @RequiresPermissions("wx:banner")0
  @RequestMapping(value = "", method = RequestMethod.GET)
  public AjaxResult<List<TBanner>> getBanner(@RequestParam(value = "loc", required = false) String location) {
    if (StringUtils.isNotEmpty(location)) {
      String dictValue = dictService.selectDictValue("operation_city",location);
      if(StrUtil.isNotEmpty(dictValue)) {
        List<TBanner> listTBanner = tBannerService.getBanner(dictValue);
        if (StringUtils.isNotEmpty(listTBanner)) {
          return AjaxResult.success("获取广告成功。", listTBanner);
        } else {
          return AjaxResult.error("无此地点数据。");
        }
      }
      else{
        return AjaxResult.error("无此地点数据。");
      }
    } else {
      return AjaxResult.error("参数错误。");
    }
  }

}
