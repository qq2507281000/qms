package co.tton.qcloud.web.controller.recommend;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.service.*;
import co.tton.qcloud.web.minio.MinioFileService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐课程
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses/recommend")
@Api(tags = "推荐课程")
public class TShopCoursesRecommendController extends BaseController
{
    private String prefix = "recommend/courses";

    @Autowired
    private ITShopCoursesRecommendService tShopCoursesRecommendService;

    @Autowired
    private ITShopCoursesService tShopCoursesService;

    @RequiresPermissions("shop:courses:recommend:list")
    @GetMapping()
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public String courses(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        if (StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")) {
            mmap.put("regionId", user.getBusinessId());
        }
        return prefix + "/list";
    }

    /**
     * 查询推荐课程列表
     */
    @RequiresPermissions("shop:courses:recommend:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取推荐课程信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public TableDataInfo list(TShopCoursesRecommendModel tShopCoursesRecommendModel)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShopCoursesRecommendModel.setRegionId(user.getBusinessId());
        }
        List<TShopCoursesRecommendModel> list = tShopCoursesRecommendService.selectTShopCoursesRecommendList(tShopCoursesRecommendModel);

        return getDataTable(list);
    }

    /**
     * 新增推荐课程
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        String regionId = null;
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            regionId = user.getBusinessId();
        }
        List<TShopCourses> tShopCourses = tShopCoursesService.selectTShopCoursesListByRegionId(regionId);
        mmap.put("courses", tShopCourses);
        return prefix + "/add";
    }

    /**
     * 新增保存推荐课程
     */
    @RequiresPermissions("shop:courses:recommend:add")
    @Log(title = "推荐课程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增推荐课程信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult addSave(TShopCoursesRecommend tShopCoursesRecommend)
    {

        int count = tShopCoursesRecommendService.insertTShopCoursesRecommend(tShopCoursesRecommend);
        if (count == 1) {
            return AjaxResult.success("推荐课程成功。",count);
        } else {
            return AjaxResult.error("推荐课程失败。");
        }

    }

    /**
     * 修改推荐课程
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        String regionId = null;
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            regionId = user.getBusinessId();
        }
        List<TShopCourses> tShopCourses = tShopCoursesService.selectTShopCoursesListByRegionId(regionId);
        mmap.put("courses", tShopCourses);
        TShopCoursesRecommend tShopCoursesRecommend = tShopCoursesRecommendService.selectTShopCoursesRecommendById(id);
        mmap.put("tShopCoursesRecommend", tShopCoursesRecommend);
        return prefix + "/edit";
    }

    /**
     * 修改保存推荐课程
     */
    @RequiresPermissions("shop:courses:recommend:edit")
    @Log(title = "推荐课程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改推荐课程信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult editSave(TShopCoursesRecommend tShopCoursesRecommend)
    {
        return toAjax(tShopCoursesRecommendService.updateTShopCoursesRecommend(tShopCoursesRecommend));
    }


    /**
     * 删除推荐课程
     */
    @RequiresPermissions("shop:courses:recommend:remove")
    @Log(title = "推荐课程", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除推荐课程信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesRecommendService.deleteTShopCoursesRecommendByIds(ids));
    }
}
