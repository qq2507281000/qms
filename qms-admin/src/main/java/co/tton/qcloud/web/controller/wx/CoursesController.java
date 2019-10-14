package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.domain.TShopCoursesPrice;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesPriceService;
import co.tton.qcloud.system.wxservice.ICoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//    @RequiresPermissions("wx:courses:suggest")
  @RequestMapping(value = "suggest", method = RequestMethod.GET)
  @ApiOperation("获取推荐课程")
  public AjaxResult<List<TShopCoursesModel>> getSuggestCourses(@RequestParam(value = "loc", required = false) String location,
                                                               @RequestParam(value = "category", required = false) String categoryId,
                                                               @RequestParam(value = "shopId", required = false) String shopId) {
    if (StringUtils.isNotEmpty(location) && location.equals("大连")) {
      TShopCoursesModel tShopCoursesModel = new TShopCoursesModel();
      if (StringUtils.isNotEmpty(shopId)) {
        tShopCoursesModel.setShopId(shopId);
      }
      if (StringUtils.isNotEmpty(categoryId)) {
        tShopCoursesModel.setCategoryId(categoryId);
      }
      List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getSuggestCourses(tShopCoursesModel);
      for (TShopCoursesModel tModel : tShopCoursesModels) {
        String id = tModel.getId();
        //匹配图片
        String imageUrl = tShopCoursesImagesService.getSuggestCoursesImages(id);
        if (imageUrl != null) {
          tModel.setImageUrl(imageUrl);
        }
        //月销
        String count = tOrderDetailService.getOrderMon(id);
        if (count != null) {
          tModel.setCount(count);
        }
      }
      return AjaxResult.success("获取推荐课程成功", tShopCoursesModels);
    } else {
      //不是大连 回传报错
      return AjaxResult.error("错误：所属地location");
    }
  }

  /***
   *
   * @param id
   * @return
   */
//    @RequiresPermissions("wx:courses:detail")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation("课程详细信息")
  public AjaxResult<TShopCoursesModel> getCoursesDetail(@PathVariable("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      TShopCoursesModel tShopCoursesModels = iCoursesService.getCoursesDetail(id);
      String count = tOrderDetailService.getOrderMon(id);
      List<TShopCoursesImages> images = tShopCoursesImagesService.getImagesByid(id);
      String[] imageUrls = new String[images.size()];
      int i = 0;
      for (TShopCoursesImages image : images) {
        imageUrls[i] = image.getImageUrl();
        i++;
      }
      tShopCoursesModels.setImages(imageUrls);
      tShopCoursesModels.setCount(count);
      return AjaxResult.success("查询成功", tShopCoursesModels);
    } else {
      return AjaxResult.error("没有获取到课程Id");
    }
  }

  //    @RequiresPermissions("wx:courses:detail")
  @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
  public AjaxResult getCoursesComment(@PathVariable("id") String id) {
    return null;
  }


  /***
   *查询所有价格信息
   * @param
   * @return
   */
//    @RequiresPermissions("wx:courses:priceAll")
  @RequestMapping(value = "/price/id", method = RequestMethod.GET)
  @ApiOperation("根据Id获取价格信息")
  public AjaxResult<List<TShopCoursesPrice>> getCoursesPriceById(@RequestParam(value = "coursesId", required = false) String coursesId) {
    if (StringUtils.isNotEmpty(coursesId)) {
      List<TShopCoursesPrice> tShopCoursesPrice = tShopCoursesPriceService.getCoursesPriceById(coursesId);
      if (tShopCoursesPrice.size() == 0) {
        return AjaxResult.error("获取失败");
      }
      return AjaxResult.success("查询成功", tShopCoursesPrice);
    }
    return AjaxResult.error("错我：课程Id为空");
  }

  /***
   *
   * @param categoryId,shopId
   * @return
   */
//    @RequiresPermissions("wx:courses:suggest")
  @RequestMapping(value = "/shop/id/category/id", method = RequestMethod.GET)
  @ApiOperation("获取某商家某分类下课程接口")
  public AjaxResult<List<TShopCoursesModel>> getSuggestCourses(@RequestParam(value = "category", required = false) String categoryId,
                                                               @RequestParam(value = "shopId") String shopId) {
    TShopCoursesModel tShopCoursesModel = new TShopCoursesModel();
    if (StringUtils.isNotEmpty(shopId)) {
      tShopCoursesModel.setShopId(shopId);
    }
    if (StringUtils.isNotEmpty(categoryId)) {
      tShopCoursesModel.setCategoryId(categoryId);
    }
    List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getShopCategoryCourses(tShopCoursesModel);
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
    return AjaxResult.success("获取成功", tShopCoursesModels);
  }

  /***
   *
   * @param shopId
   * @return
   */
//    @RequiresPermissions("wx:courses:suggest")
  @RequestMapping(value = "/shop/category", method = RequestMethod.GET)
  @ApiOperation("获取商家所有课程分类接口")
  public AjaxResult<List<TShopCoursesModel>> getAllCoursesCategory(@RequestParam(value = "shopId") String shopId) {
    TShopCoursesModel tShopCoursesModel = new TShopCoursesModel();
    if (StringUtils.isNotEmpty(shopId)) {
      tShopCoursesModel.setShopId(shopId);
    }
    List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getAllCoursesCategory(tShopCoursesModel);
    tShopCoursesModels.forEach(parent ->{

    });
    return AjaxResult.success("获取成功", tShopCoursesModels);
  }

}
