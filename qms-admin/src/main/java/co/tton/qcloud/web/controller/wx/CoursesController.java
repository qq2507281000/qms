package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesPriceService;
import co.tton.qcloud.system.wxservice.ICoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:32
 */

@RestController
@RequestMapping("/api/v1.0/courses")
@Api(tags = "小程序课程页面")
public class CoursesController extends BaseController {

    @Autowired
    private ICoursesService iCoursesService;

    @Autowired
    private ITShopCoursesImagesService tShopCoursesImagesService;

    @Autowired
    private ITOrderDetailService tOrderDetailService;

    @Autowired
    private ITShopCoursesPriceService tShopCoursesPriceService;


    /***
     *
     * @param location,categoryId,shopId
     * @return
     */
    @RequiresPermissions("wx:courses:suggest")
    @RequestMapping(value = "suggest",method = RequestMethod.GET)
    @ApiOperation("获取推荐课程")
    public AjaxResult<List<TShopCoursesModel>> getSuggestCourses(@RequestParam(value="loc",required = false)String location,
                                        @RequestParam(value="category",required = false)String categoryId,
                                        @RequestParam(value="shopId",required = false)String shopId){
        if (StringUtils.isNotEmpty(location) && location.equals("大连")){
                //选择类型了的推荐
            String cId = null;
            String sId= null;
            if(shopId != null && !shopId.trim().equals("")){
                sId = shopId;
            }
            if (categoryId != null && !categoryId.trim().equals("")){
                cId = categoryId;
            }
                List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getSuggestCourses(cId,sId);
                for (TShopCoursesModel tModel : tShopCoursesModels) {
                    String id = tModel.getId();
                    String imageUrl = tShopCoursesImagesService.getSuggestCoursesImages(id);
                    if (imageUrl != null) {
                        tModel.setImageUrl(imageUrl);
                    }
                    String count = tOrderDetailService.getOrderMon(id);
                    if (count != null) {
                        tModel.setCount(count);
                    }
                }
                return AjaxResult.success("获取推荐课程成功", tShopCoursesModels);


        }
        else {
            //不是大连 回传报错
            return AjaxResult.error("错误：所属地location");
        }

    }


    /***
     *
     * @param id
     * @return
     */
    @RequiresPermissions("wx:courses:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation("课程详细信息")
    public AjaxResult<TShopCoursesModel> getCoursesDetail(@PathVariable("id") String id){
        if(StringUtils.isNotEmpty(id)){
            TShopCoursesModel tShopCoursesModels = iCoursesService.getCoursesDetail(id);
            String count = tOrderDetailService.getOrderMon(id);
            List<TShopCoursesImages> images = tShopCoursesImagesService.getImagesByid(id);
            String[] imageUrls = new String[images.size()];
            int i = 0;
            for(TShopCoursesImages image:images){
                imageUrls[i] = image.getImageUrl();
                i++;
            }
            tShopCoursesModels.setImages(imageUrls);
            tShopCoursesModels.setCount(count);
            return AjaxResult.success("查询成功",tShopCoursesModels);
        }
        else {
            return AjaxResult.error("没有获取到课程Id");
        }
    }

    @RequiresPermissions("wx:courses:detail")
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public AjaxResult getCoursesComment(@PathVariable("id") String id){
        return null;
    }



    /***
     *查询所有价格信息
     * @param
     * @return
     */
    @RequiresPermissions("wx:courses:priceAll")
    @RequestMapping(value = "/price",method = RequestMethod.GET)
    @ApiOperation("根据Id获取价格信息")
    public AjaxResult<List<TShopCoursesPrice>> getCoursesPriceById(@RequestParam(value="coursesId",required = false)String coursesId){
        if (StringUtils.isNotEmpty(coursesId)){
            List<TShopCoursesPrice> tShopCoursesPrice = tShopCoursesPriceService.getCoursesPriceById(coursesId);
            if(tShopCoursesPrice.size() == 0){
                return AjaxResult.error("获取失败");
            }
            return AjaxResult.success("查询成功",tShopCoursesPrice);
        }
        return AjaxResult.error("错我：课程Id为空");
    }
}
