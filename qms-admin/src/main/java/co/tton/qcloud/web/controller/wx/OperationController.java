package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.wxservice.ICoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "小程序首页弹窗",tags = "小程序首页弹窗")
public class OperationController extends BaseController {

    @Autowired
    private ICoursesService iCoursesService;
    @Autowired
    private ITShopCoursesImagesService tShopCoursesImagesService;
//    @RequiresPermissions("wx:operation:popup")
    @RequestMapping(value = "/popup",method = RequestMethod.GET)
    @ApiOperation("获取弹窗")
    public AjaxResult<TShopCoursesModel> indexPopup(){
            TShopCourses tShopCourses = new TShopCourses();
            TShopCoursesModel tShopCoursesModel1 = iCoursesService.getMaxSortKeyCourses(tShopCourses);
            if(StringUtils.isNotNull(tShopCoursesModel1)){
                String imageUrl = tShopCoursesImagesService.getSuggestCoursesImages(tShopCoursesModel1.getId());
                if(StringUtils.isNotEmpty(imageUrl)){
                    tShopCoursesModel1.setImageUrl(imageUrl);
                    return AjaxResult.success("获取成功",tShopCoursesModel1);
                }else {
                    return AjaxResult.success("获取失败");
                }
            }else {
                return AjaxResult.success("获取失败");
            }
    }

}
