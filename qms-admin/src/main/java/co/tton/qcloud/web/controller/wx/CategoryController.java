package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:21
 */

@RestController
@RequestMapping("/api/v1.0/category")
public class CategoryController extends BaseController {

    @RequiresPermissions("wx:category:top")
    @RequestMapping(value="/top",method = RequestMethod.GET)
    public AjaxResult getTopCatgory(){
        //TODO:获取顶级分类信息
        return AjaxResult.success("获取顶级分类成功。",null);
    }

    @RequiresPermissions("wx:category:all")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public AjaxResult getAllCategory(){
        //TODO:获取所有分类信息
        return AjaxResult.success("获取所有分类信息成功。",null);
    }

    @RequiresPermissions("wx:category:search")
    @RequestMapping(value="/search",method = RequestMethod.GET)
    public AjaxResult searchCategory(@RequestParam("key")String searchKey){
        return null;
    }

    @RequiresPermissions("wx:category:sub")
    @RequestMapping(value = "/{pid}",method = RequestMethod.GET)
    public AjaxResult getSubCategory(@PathVariable("pid") String parentId){
        return null;
    }

}
