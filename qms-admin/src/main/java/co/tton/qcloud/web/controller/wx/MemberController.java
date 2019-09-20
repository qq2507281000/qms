package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:34
 */

@RestController
@RequestMapping("/api/v1.0/member")
public class MemberController extends BaseController {

    @RequiresPermissions("wx:member:info")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public AjaxResult getMemberInfo(@PathVariable("id") String memberId){
        return null;
    }

    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/orders",method = RequestMethod.GET)
    public AjaxResult getOrderInfo(@PathVariable("id") String memberId){
        return null;
    }

    @RequiresPermissions("wx:member:favourite")
    @RequestMapping(value="/favourite")
    public AjaxResult getFavourite(){
        return null;
    }


    public AjaxResult autoLogin(){
        return null;
    }

}
