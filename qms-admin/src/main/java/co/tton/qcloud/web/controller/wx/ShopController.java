package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.domain.TShopCoursesModel;
import co.tton.qcloud.system.service.ISysDictDataService;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.system.wxservice.ITShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-23
 */
@Api(value = "小程序商家信息", tags = "小程序商家信息")
@RestController
@RequestMapping("/api/v1.0/shop")
public class ShopController extends BaseController {

  @Autowired
  private ITShopService tShopService;
  @Autowired
  private ITShopCoursesService tShopCoursesService;
  @Autowired
  private ITShopCoursesImagesService tShopCoursesImagesService;
  @Autowired
  private ITOrderDetailService tOrderDetailService;

  @Autowired
  private ISysDictDataService dictService;

  @ApiOperation("首页推荐商家查询，查询所有商家，根据分类查商家")
//    @RequiresPermissions("wx:shop:suggest")
  @RequestMapping(value = "/suggest", method = RequestMethod.GET)
  public AjaxResult<List<TShop>> getSuggestShop(@RequestParam(value = "loc", required = false) String location,
                                                @RequestParam(value = "category", required = false) String categoryId,
                                                @RequestParam(value = "suggest", required = false) Integer suggest) {

    String regionId = "";

    if(StringUtils.isNotEmpty(location)){
      regionId = dictService.selectDictValue("operation_city",location);
    }

    TShop tShop = new TShop();
    //tShop.setAddress(location);//查询本地区所有商家条件
    tShop.setRegionId(regionId);
    tShop.setCategoryId(categoryId);//根据分类查商家条件
    List<TShop> listTShop = null;
    if (suggest !=null && 0 == suggest) {
      tShop.setSuggest(suggest);//首页推荐商家查询条件
      listTShop = tShopService.getSuggestShop(tShop);
    } else {
      listTShop = tShopService.getSuggestShopAll(tShop);
    }

    if (StringUtils.isNotNull(listTShop) && (listTShop.size() != 0)) {
      return AjaxResult.success("获取商家信息成功。", listTShop);
    } else {
      return AjaxResult.error("获取商家信息失败。");
    }
  }

  @ApiOperation("查询所有商家")
//    @RequiresPermissions("wx:shop:suggest")
  @RequestMapping(value = "/suggestAll", method = RequestMethod.GET)
  public AjaxResult<List<TShop>> getSuggestShopAll(@RequestParam(value = "loc", required = false) String location,
                                                @RequestParam(value = "category", required = false) String categoryId,
                                                @RequestParam(value = "suggest", required = false) Integer suggest) {

    String regionId = "";

    if(StringUtils.isNotEmpty(location)){
      regionId = dictService.selectDictValue("operation_city",location);
    }

    TShop tShop = new TShop();
//    tShop.setAddress(location);//查询本地区所有商家条件
    tShop.setRegionId(regionId);
    tShop.setCategoryId(categoryId);//根据分类查商家条件
    tShop.setSuggest(suggest);//首页商家查询条件
    List<TShop> listTShop = tShopService.getSuggestShopAll(tShop);
    if (StringUtils.isNotNull(listTShop) && (listTShop.size() != 0)) {
      return AjaxResult.success("获取商家信息成功。", listTShop);
    } else {
      return AjaxResult.error("获取商家信息失败。");
    }
  }

  @ApiOperation("根据分类查商家")
//    @RequiresPermissions("wx:shop:suggest")
  @RequestMapping(value = "/getSuggestShopByCategory", method = RequestMethod.GET)
  public AjaxResult<List<TShop>> getSuggestShopByCategory(@RequestParam(value = "loc", required = false) String location,
                                                @RequestParam(value = "category", required = false) String categoryId,
                                                @RequestParam(value = "suggest", required = false) Integer suggest) {

    String regionId = "";

    if(StringUtils.isNotEmpty(location)){
      regionId = dictService.selectDictValue("operation_city", location);
    }

    TShop tShop = new TShop();
//    tShop.setAddress(location);//查询本地区所有商家条件
    tShop.setRegionId(regionId);
    tShop.setCategoryId(categoryId);//根据分类查商家条件
//    tShop.setSuggest(suggest);//首页推荐商家查询条件
    List<TShop> listTShop = tShopService.getSuggestShopByCategory(tShop);
    if (StringUtils.isNotNull(listTShop) && (listTShop.size() != 0)) {
      return AjaxResult.success("获取商家信息成功。", listTShop);
    } else {
      return AjaxResult.error("获取商家信息失败。");
    }
  }


  @ApiOperation("查询商家详情")
//    @RequiresPermissions("wx:shop:detail")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public AjaxResult<List<TShop>> getShopDetail(@PathVariable("id") String shopId,
                                               @RequestParam(value = "loc", required = false) String location) {

    String regionId = "";

    if(StringUtils.isNotEmpty(location)){
      regionId = dictService.selectDictValue("operation_city",location);
    }

    if (StringUtils.isNotEmpty(shopId)) {
      TShop tShop = new TShop();
      tShop.setId(shopId);
//      tShop.setAddress(location);
      tShop.setRegionId(regionId);
      List list = tShopService.getShopDetail(tShop);//查询商家详情
      if (StringUtils.isNotEmpty(list)) {
        return AjaxResult.success("获取商家详情成功。", list);
      } else {
        return AjaxResult.success("获取商家详情失败。", list);
      }
    } else {
      return AjaxResult.error("商家ID错误。");
    }
  }

  @ApiOperation("搜索框课程商家查询")
//  @RequiresPermissions("wx:name")
  @RequestMapping(value = "/getName", method = RequestMethod.GET)
  public AjaxResult<List> getName(@RequestParam(value = "name") String name,
                                  @RequestParam(value = "loc", required = false) String location) {
    if (StringUtils.isNotEmpty(name)) {
      String regionId = "";
      TShop shop = new TShop();
      if(StringUtils.isNotEmpty(location)){
        regionId = dictService.selectDictValue("operation_city", location);
        shop.setRegionId(regionId);
      }
      shop.setName(name);
      //查询商家表
      List listTShop = tShopService.getNameShop(shop);
      List list = new ArrayList();
      Map<String,List> map = new HashMap();
      if (StringUtils.isNotEmpty(listTShop)) {
        map.put("shop",listTShop);
      }
      //查询课程表
      TShopCourses shopCourses = new TShopCourses();
      shopCourses.setTitle(name);
      shopCourses.setRegionId(regionId);
      List<TShopCoursesModel> listTShopCourses = tShopCoursesService.getNameShopCourses(shopCourses);
      if (StringUtils.isNotEmpty(listTShopCourses)) {
        for (TShopCoursesModel tModel : listTShopCourses) {
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
        map.put("courses",listTShopCourses);
        list.add(map);
      }
      if (StringUtils.isNotEmpty(list)) {
        return AjaxResult.success("获取信息成功。", list);
      } else {
        return AjaxResult.error("无数据。");
      }
    } else {
      return AjaxResult.error("参数错误。");
    }
  }

}
