package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:24
 */

@RestController
@RequestMapping("/api/v1.0/location")
public class LocationController extends BaseController {

    @RequiresPermissions("wx:location")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public AjaxResult getLocaltions(){
        //TODO:获取所有城市列表信息
        return AjaxResult.success("获取所有城市列表信息成功。",null);
    }

}
