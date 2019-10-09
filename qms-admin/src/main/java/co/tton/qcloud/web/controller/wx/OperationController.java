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
 * @create: 2019-09-18 18:42
 */

@RestController
@RequestMapping("/api/v1.0")
public class OperationController extends BaseController {

//    @RequiresPermissions("wx:operation:popup")
    @RequestMapping(value = "/popup",method = RequestMethod.GET)
    public AjaxResult indexPopup(@RequestParam(value="loc",required = false) String location){
        return null;
    }

}
