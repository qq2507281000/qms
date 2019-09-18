package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:16
 */


@RestController
@RequestMapping("/api/v1.0/banner")
public class BannerController extends BaseController {

    @RequiresPermissions("wx:banner")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public AjaxResult getBanner(@RequestParam(value = "loc",required = false)String location){
        //TODO:获取首页Banner信息，需要提供可选参数地理位置信息。
        return AjaxResult.success("获取Banner信息成功。",null);
    }

}
