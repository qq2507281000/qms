package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TCategory;
import co.tton.qcloud.system.service.ITCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:21
 */

@RestController
@RequestMapping("/api/v1.0/category")
@Api(tags = "小程序课程分类")
public class CategoryController extends BaseController {

  @Autowired
  private ITCategoryService tCategoryService;

//    @RequiresPermissions("wx:category:top")
  @RequestMapping(value = "/top", method = RequestMethod.GET)
  @ApiOperation("获取顶级分类信息")
  public AjaxResult<List<TCategory>> getAllCategoryByMap() {
    //获取顶级分类信息
    Map<String, Object> map = new HashMap();
    map.put("parent", 1);
    List<TCategory> tCategories = tCategoryService.getAllCategoryByMap(map);
    return AjaxResult.success("获取顶级分类成功。", tCategories);
  }

//    @RequiresPermissions("wx:category:all")
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ApiOperation("获取所有分类信息")
  public AjaxResult<List<TCategory>> getAllCategory() {
    Map<String, Object> map = new HashMap<>();
    map.put("parent", 1);
    //获取一级分类
    List<TCategory> tAllCategories = tCategoryService.getAllCategoryByMap(map);
    tAllCategories.forEach(parent -> {
      //根据一级分类获取二级分类
      List<TCategory> childList = tCategoryService.getChildList(parent.getId());
      parent.setChildren(childList);
    });
    return AjaxResult.success("获取所有分类信息成功。", tAllCategories);
  }

//    @RequiresPermissions("wx:category:search")
  @RequestMapping(value = "/search", method = RequestMethod.GET)
  @ApiOperation("搜索框查询")
  public AjaxResult<List<TCategory>> searchCategory(@RequestParam("key") String searchKey) {
    //搜索框查询
    if (StringUtils.isNotEmpty(searchKey)) {
      List<TCategory> tCategories = tCategoryService.searchCategory(searchKey);
      if(StringUtils.isNotEmpty(tCategories)){
        return AjaxResult.success("搜索成功。", tCategories);
      }else{
        return AjaxResult.error("无此条件数据。");
      }
    } else {
      return AjaxResult.error("参数错误。");
    }
  }

//    @RequiresPermissions("wx:category:sub")
  @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
  @ApiOperation("根据顶级分类搜索子级分类")
  public AjaxResult<List<TCategory>> getSubCategory(@PathVariable("pid") String parentId) {
    if (StringUtils.isNotEmpty(parentId)) {
      //根据顶级分类搜索子级分类查询
      List<TCategory> tCategories = tCategoryService.getSubCategory(parentId);
      if(StringUtils.isNotEmpty(tCategories)){
        return AjaxResult.success("获取所有分类信息成功。", tCategories);
      }else{
        return AjaxResult.error("无此条件数据。");
      }
    } else {
      return AjaxResult.error("参数错误。");
    }
  }

}
