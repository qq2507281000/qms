package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.domain.TCategory;
import co.tton.qcloud.system.service.ITCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequiresPermissions("wx:category:top")
    @RequestMapping(value="/top",method = RequestMethod.GET)
    @ApiOperation("获取顶级分类信息")
    public AjaxResult getTopCatgory(){
        //获取顶级分类信息
        List<TCategory> tCategories = tCategoryService.getTopCatgory();
        return AjaxResult.success("获取顶级分类成功。",tCategories);
    }

    @RequiresPermissions("wx:category:all")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation("获取所有分类信息")
    public AjaxResult getAllCategory(){
        //获取所有分类信息
        List<TCategory> tAllCategories = tCategoryService.getAllCategory();
        return AjaxResult.success("获取所有分类信息成功。",tAllCategories);
    }

    @RequiresPermissions("wx:category:search")
    @RequestMapping(value="/search",method = RequestMethod.GET)
    @ApiOperation("搜索框查询")
    public AjaxResult searchCategory(@RequestParam("key")String searchKey){
        //搜索框查询
        List<TCategory> tCategories =tCategoryService.searchCategory(searchKey);
        return AjaxResult.success("获取所有分类信息成功。",tCategories);
    }

    @RequiresPermissions("wx:category:sub")
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    @ApiOperation("根据顶级分类搜索子级分类")
    public AjaxResult getSubCategory(@PathVariable("pid") String parentId){
        //根据顶级分类搜索子级分类
        List<TCategory> tCategories = tCategoryService.getSubCategory(parentId);
        return AjaxResult.success("获取所有分类信息成功。",tCategories);
    }

}
