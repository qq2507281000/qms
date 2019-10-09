package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TCouponUseLogModel;
import co.tton.qcloud.system.wxservice.ITCouponService;
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
 * @create: 2019-09-18 18:54
 */

@Api(value = "小程序优惠卷",tags="小程序优惠卷")
@RestController
@RequestMapping("/api/v1.0/coupon")
public class CouponController extends BaseController {

    @Autowired
    ITCouponService iTCouponService;

    /***
     *
     * @param memberId
     * @return
     */
    @ApiOperation("会员用户优惠劵查询")
//    @RequiresPermissions("wx:coupon:list")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public AjaxResult<List<TCouponUseLogModel>> getCouponList(@RequestParam(value = "id") String memberId){
        if(StringUtils.isNotEmpty(memberId)){
            List<TCouponUseLogModel> list = iTCouponService.getCouponList(memberId);
            if(StringUtils.isNotEmpty(list)){
                return AjaxResult.success("优惠劵查询成功",list);
            }else{
                return AjaxResult.success("查询失败");
            }
        }else{
            return AjaxResult.error("会员ID错误");
        }
    }

}
