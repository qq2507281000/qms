package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TCoupon;
import co.tton.qcloud.system.domain.TCouponUseLogModel;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesPrice;
import co.tton.qcloud.system.model.OrderPriceModel;
import co.tton.qcloud.system.service.ITCouponService;
import co.tton.qcloud.system.service.ITShopCoursesPriceService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ITCouponService couponService;
    @Autowired
    private ITShopCoursesService coursesService;
    @Autowired
    private ITShopCoursesPriceService coursesPriceService;

    @ApiOperation("会员用户优惠劵查询")
//    @RequiresPermissions("wx:coupon:list")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public AjaxResult<List<TCoupon>> getCouponList(@RequestParam(value = "memberId") String memberId){
        try {
            if (StrUtil.isNotEmpty(memberId)) {
                List<TCoupon> list = couponService.selectTCouponByMemberId(memberId);//查询满足状态的优惠卷
                if(StringUtils.isNotEmpty(list)){
                    return AjaxResult.success("获取会员优惠券成功。",list);
                }else{
                    return AjaxResult.error("该用户没有优惠卷。");
                }
            }
            else{
                return error("参数错误。");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("查询用户优惠券失败。",ex);
            return error(ex.getMessage());
        }
    }

    @ApiOperation("使用优惠券接口")
    @RequestMapping(value = "/use",method = RequestMethod.GET)
    public AjaxResult useCouponList(@RequestParam("couponId")String couponId,
                                    @RequestParam("coursesId")String coursesId,
                                    @RequestParam("priceId")String priceId){
        try{
            if(StrUtil.isNotEmpty(coursesId)){
                TShopCourses courses = coursesService.selectTShopCoursesById(coursesId);
                if(courses == null){
                    return error("未能找到课程信息。");
                }
                else{
                    TShopCoursesPrice coursesPrice = coursesPriceService.selectTShopCoursesPriceById(priceId);
                    double price = coursesPrice.getPrice();//查出默认基准价格
                    TCoupon coupon = couponService.selectTCouponById(couponId);
                    double faceValue = coupon.getFaceValue();//查出优惠卷面值
                    double realPrice = price - faceValue;//计算出应付价格
                    OrderPriceModel priceModel = new OrderPriceModel();
                    priceModel.setCoursesId(coursesId);
                    priceModel.setPriceId(priceId);
                    priceModel.setCouponId(couponId);
                    priceModel.setCoursesPrice(price);
                    priceModel.setCouponFaceValue(faceValue);
                    priceModel.setRealPrice(realPrice);

                    return AjaxResult.success("优惠券计算结果成功。",priceModel);
                }
            }
            else{
                return error("参数错误，课程Id不允许为空。");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
            logger.error("使用优惠券接口时发生异常。",ex);
            return error(ex.getMessage());
        }
    }

}
