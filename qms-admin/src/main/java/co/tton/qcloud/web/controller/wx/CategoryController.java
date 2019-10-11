package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TCategory;
import co.tton.qcloud.system.service.ITCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * @create: 2019-09-18 18:21
 */

@RestController
@RequestMapping("/api/v1.0/category")
@Api(tags = "小程序课程分类")
public class CategoryController extends BaseController {

    @Autowired
    private ITCategoryService tCategoryService;

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:category:top")
    @RequestMapping(value="/top",method = RequestMethod.GET)
    @ApiOperation("获取顶级分类信息")
    public AjaxResult<List<TCategory>> getTopCatgory(){
        //获取顶级分类信息
        List<TCategory> tCategories = tCategoryService.getTopCatgory();
        return AjaxResult.success("获取顶级分类成功。",tCategories);
    }

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:category:all")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation("获取所有分类信息")
    public AjaxResult<List<TCategory>> getAllCategory(){
        //查询所有不重复parentId信息
//        List<TCategory> tAllCategories = tCategoryService.getAllCategory(null);
//        List list = new ArrayList();
//        for(int i=0;i<tAllCategories.size();i++){
//            String s = tAllCategories.get(i).getParentId();
//            if(!list.contains(s)) {
//                list.add(s);
//            }
//        }
//        Map <String,List> map = new HashMap();
        Map <String,Object> map = new HashMap();
        map.put("parent",1);
        List<TCategory> tAllCategories = tCategoryService.getAllCategoryByMap(map);
        tAllCategories.forEach(parent ->{
            List<TCategory> childList = tCategoryService.getChildList(parent.getId());
            parent.setChildTCategoryList(childList);
        });
//        //根据parentId查询信息
//        for(int j=0;j<list.size();j++){
//            String str=list.get(j).toString();
//            if(StringUtils.isNotEmpty(str)){
//                List tCategoryList=tCategoryService.getAllCategory(str);
//                map.put(str,tCategoryList);
//            }
//        }
        return AjaxResult.success("获取所有分类信息成功。",tAllCategories);
    }

//    /***
//     *
//     * @param
//     * @return
//     */
////    @RequiresPermissions("wx:category:all")
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    @ApiOperation("获取所有分类信息")
//    public AjaxResult<List<TCategory>> getAllCategory(){
//        //获取所有分类信息
//        List<TCategory> tAllCategories = tCategoryService.getAllCategory();
//        return AjaxResult.success("获取所有分类信息成功。",tAllCategories);
//    }

    /***
     *
     * @param searchKey
     * @return
     */
//    @RequiresPermissions("wx:category:search")
    @RequestMapping(value="/search",method = RequestMethod.GET)
    @ApiOperation("搜索框查询")
    public AjaxResult<List<TCategory>> searchCategory(@RequestParam("key")String searchKey){
        //搜索框查询
        if (StringUtils.isNotEmpty(searchKey)) {
            List<TCategory> tCategories = tCategoryService.searchCategory(searchKey);
            return AjaxResult.success("获取所有分类信息成功。", tCategories);
        }else {
            return AjaxResult.error("searchKey为空。");
        }
    }

    /***
     *
     * @param parentId
     * @return
     */
//    @RequiresPermissions("wx:category:sub")
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    @ApiOperation("根据顶级分类搜索子级分类")
    public AjaxResult<List<TCategory>> getSubCategory(@PathVariable("pid") String parentId){
        //根据顶级分类搜索子级分类
        if (StringUtils.isNotEmpty(parentId)) {
            List<TCategory> tCategories = tCategoryService.getSubCategory(parentId);
            return AjaxResult.success("获取所有分类信息成功。", tCategories);
        }else {
            return AjaxResult.error("parentId为空。");
        }
    }

}
