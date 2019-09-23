package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.wxservice.ITBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

@Api(value = "微信首页广告信息",tags="微信首页广告信息")
@RestController
@RequestMapping("/api/v1.0/banner")
public class BannerController extends BaseController {

    @Autowired
    private ITBannerService tBannerService;

    @ApiOperation("微信查询首页滚动广告")
    @RequiresPermissions("wx:banner")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public AjaxResult getBanner(@RequestParam(value = "loc",required = false)String location){
        if(location.equals("dalian")){
            List list=tBannerService.getBanner();
            return AjaxResult.success("获取广告成功。",list);
        }else{
            return AjaxResult.success("location错误。");
        }
    }

}
