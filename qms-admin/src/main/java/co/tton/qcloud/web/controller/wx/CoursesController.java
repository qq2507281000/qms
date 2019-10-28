package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.model.ShopCoursesListModel;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesPriceService;
import co.tton.qcloud.system.service.ITShopCoursesTimeService;
import co.tton.qcloud.system.wxservice.ICoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
  @Autowired
  private ITShopCoursesTimeService shopCoursesTimeService;

  //    @RequiresPermissions("wx:courses:suggest")
  @RequestMapping(value = "suggest", method = RequestMethod.GET)
  @ApiOperation("获取推荐课程,查询所有课程")
  public AjaxResult<List<TShopCoursesModel>> getSuggestCourses(@RequestParam(value = "loc", required = false) String location,
                                                               @RequestParam(value = "category", required = false) String categoryId,
                                                               @RequestParam(value = "shopId", required = false) String shopId,
                                                               @RequestParam(value = "suggest", required = false) Integer suggest) {
    TShopCoursesModel tShopCoursesModel = new TShopCoursesModel();
    if (StringUtils.isNotEmpty(location)) {
      tShopCoursesModel.setAddress(location);
    }
    if (StringUtils.isNotEmpty(shopId)) {
      tShopCoursesModel.setShopId(shopId);
    }
    if (StringUtils.isNotEmpty(categoryId)) {
      tShopCoursesModel.setCategoryId(categoryId);
    }
      tShopCoursesModel.setSuggest(suggest);
    //查出课程
    List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getSuggestCourses(tShopCoursesModel);
    if (StringUtils.isNotEmpty(tShopCoursesModels)) {
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
      return AjaxResult.success("无推荐课程", tShopCoursesModels);
    }
  }

  //    @RequiresPermissions("wx:courses:detail")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ApiOperation("课程详细信息")
  public AjaxResult<TShopCoursesModel> getCoursesDetail(@PathVariable("id") String id) {
    if (StringUtils.isNotEmpty(id)) {
      TShopCoursesModel tShopCoursesModels = iCoursesService.getCoursesDetail(id);//查询课程详情
      String count = tOrderDetailService.getOrderMon(id);//计算月销
      List<TShopCoursesImages> images = tShopCoursesImagesService.getImagesByid(id);//查询出图片
      String[] imageUrls = new String[images.size()];
      int i = 0;
      //匹配图片
      for (TShopCoursesImages image : images) {
        imageUrls[i] = image.getImageUrl();
        i++;
      }
      tShopCoursesModels.setImages(imageUrls);
      tShopCoursesModels.setCount(count);

      TShopCoursesTime query = new TShopCoursesTime();
      query.setCoursesId(id);

      //查询上课时间
      List<TShopCoursesTime> shopCoursesTimes = shopCoursesTimeService.selectTShopCoursesTimeList(query);
      tShopCoursesModels.setCoursesTimeList(shopCoursesTimes);

      return AjaxResult.success("查询成功", tShopCoursesModels);
    } else {
      return AjaxResult.error("参数错误");
    }
  }

  //    @RequiresPermissions("wx:courses:price")
  @RequestMapping(value = "/price/id", method = RequestMethod.GET)
  @ApiOperation("根据课程Id获取价格分类")
  public AjaxResult<List<TShopCoursesPrice>> getCoursesPriceById(@RequestParam(value = "coursesId", required = false) String coursesId) {
    if (StringUtils.isNotEmpty(coursesId)) {
      List<TShopCoursesPrice> tShopCoursesPrice = tShopCoursesPriceService.getCoursesPriceById(coursesId);//查询价格信息
      if (tShopCoursesPrice.size() == 0) {
        return AjaxResult.error("获取失败");
      }
      return AjaxResult.success("查询成功", tShopCoursesPrice);
    }
    return AjaxResult.error("参数错误");
  }

  //    @RequiresPermissions("wx:shop:category")
  @RequestMapping(value = "/category/id", method = RequestMethod.GET)
  @ApiOperation("获取某商家某分类下课程接口")
  public AjaxResult<List<TShopCoursesModel>> getSuggestCourses(@RequestParam(value = "categoryid", required = false) String categoryId,
                                                               @RequestParam(value = "shopid") String shopId) {
    TShopCoursesModel tShopCoursesModel = new TShopCoursesModel();
    if (StringUtils.isNotEmpty(shopId)) {
      tShopCoursesModel.setShopId(shopId);
    }
    if (StringUtils.isNotEmpty(categoryId)) {
      tShopCoursesModel.setCategoryId(categoryId);
    }
    {
      //获取某商家某分类下课程信息
      List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getShopCategoryCourses(tShopCoursesModel);
      if (StringUtils.isNotEmpty(tShopCoursesModels)) {
        for (TShopCoursesModel tModel : tShopCoursesModels) {
          String id = tModel.getId();
          String imageUrl = tShopCoursesImagesService.getSuggestCoursesImages(id);//匹配图片
          if (imageUrl != null) {
            tModel.setImageUrl(imageUrl);
          }
          String count = tOrderDetailService.getOrderMon(id);//匹配月销
          if (count != null) {
            tModel.setCount(count);
          }
        }
        return AjaxResult.success("获取成功", tShopCoursesModels);
      } else {
        return AjaxResult.success("该参数下无查询结果", tShopCoursesModels);
      }
    }
  }

  //    @RequiresPermissions("wx:shop:all:category")
  @RequestMapping(value = "/shop/category", method = RequestMethod.GET)
  @ApiOperation("获取商家所有课程分类")
  public AjaxResult<List<TShopCoursesModel>> getAllCoursesCategory(@RequestParam(value = "shopId") String shopId) {

    if (StringUtils.isNotEmpty(shopId)) {
      TShopCoursesModel tShopCoursesModel = new TShopCoursesModel();
      tShopCoursesModel.setShopId(shopId);
      //获取商家所有课程分类
      List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getAllCoursesCategory(tShopCoursesModel);
      if (StringUtils.isNotEmpty(tShopCoursesModels)) {
        return AjaxResult.success("获取成功", tShopCoursesModels);
      } else {
        return AjaxResult.error("该商家没有任何分类");
      }
    } else {
      return AjaxResult.error("参数错误");
    }
  }

  //    @RequiresPermissions("wx:courses:evaluation")
  @RequestMapping(value = "/courses/evaluation", method = RequestMethod.GET)
  @ApiOperation("获取课程评价")
  public AjaxResult<List<TOrderUseEvaluation>> getCoursesCategory(@RequestParam(value = "coursesid") String coursesId) {

    if (StringUtils.isNotEmpty(coursesId)) {
      //查询课程评价信息
      List<TOrderUseEvaluation> tShopCoursesModel = iCoursesService.getCoursesCategory(coursesId);
      if (StringUtils.isNotEmpty(tShopCoursesModel)) {
        for (TOrderUseEvaluation tOrderUseEvaluation : tShopCoursesModel) {
          //获取该索引下的图片字符串
          String str = tOrderUseEvaluation.getImageUrl();
          if (str != null && !str.equals("")) {
            //解析字符串
            String[] arr = str.split(",");
            tOrderUseEvaluation.setImageUrls(Arrays.asList(arr));
          }
        }
        return AjaxResult.success("获取成功", tShopCoursesModel);
      } else {
        return AjaxResult.error("该课程无评价");
      }
    } else {
      return AjaxResult.error("参数错误");
    }
  }

  @ApiOperation("收藏搜索框课程查询")
//  @RequiresPermissions("wx:courses:name")
  @RequestMapping(value = "/name", method = RequestMethod.GET)
  public AjaxResult<List> getCollectionCourses(@RequestParam(value = "coursestitle") String coursesName,
                                      @RequestParam(value = "memberid") String memberId) {
    if (StringUtils.isNotEmpty(memberId)&&StringUtils.isNotEmpty(coursesName)) {
      //收藏表收藏课程查询
      List<TShopCoursesModel> tShopCoursesModels = iCoursesService.getcollectionCourses(coursesName,memberId);
      if (StringUtils.isNotEmpty(tShopCoursesModels)) {
        for (TShopCoursesModel tModel : tShopCoursesModels) {
          String id = tModel.getId();
          String imageUrl = tShopCoursesImagesService.getSuggestCoursesImages(id);//匹配图片
          if (imageUrl != null) {
            tModel.setImageUrl(imageUrl);
          }
          String count = tOrderDetailService.getOrderMon(id);//月销
          if (count != null) {
            tModel.setCount(count);
          }
        }
        return AjaxResult.success("获取成功", tShopCoursesModels);
      } else {
        return AjaxResult.error("该参数下无查询结果");
      }
    } else {
      return AjaxResult.error("参数错误");
    }
  }

  /***
   * 获取月销课程数据
   * @param location 位置信息
   * @return
   */
  @ApiOperation("获取月销课程数据")
  @RequestMapping(value = "/latest",method = RequestMethod.GET)
  public AjaxResult<List<ShopCoursesListModel>> getLatestCourses(@ApiParam("城市名称") @RequestParam(value = "loc",required = false) String location){
    List<ShopCoursesListModel> list = new ArrayList<>();

    ShopCoursesListModel model = new ShopCoursesListModel();
    model.setShopId("438cfa5de661430a910d04c15ef24360");
    model.setCoursesId("409c597d36b23d055a34b85751115dc0");
    model.setCoverImage("42f8484165b34258939f761605efb52620190927.jpg");
    model.setShopName("大连致纯足球");
    model.setSaleCount(29);
    model.setStartPrice(2.99);
    model.setTitle("攀岩");
    list.add(model);

    return AjaxResult.success("获取月销成功。", list);
  }

}
