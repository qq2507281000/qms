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
 * @create: 2019-09-18 18:54
 */

@RestController
@RequestMapping("/api/v1.0/coupon")
public class CouponController extends BaseController {

    @RequiresPermissions("wx:coupon:list")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public AjaxResult getCouponList(){
        return null;
    }

}
