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
 * 开机推荐Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses/starting")
@Api(tags = "开机推荐")
public class TShopCoursesStartingController extends BaseController
{
    private String prefix = "recommend/starting";

    @Autowired
    private ITShopCoursesStartingService tShopCoursesStartingService;

    @Autowired
    private ITShopCoursesService tShopCoursesService;

    @Autowired
    private ITShopService tShopService;

    @RequiresPermissions("shop:courses:starting:list")
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
     * 查询开机推荐列表
     */
    @RequiresPermissions("shop:courses:starting:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取开机推荐信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public TableDataInfo list(TShopCoursesStartingModel tShopCoursesStartingModel)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShopCoursesStartingModel.setRegionId(user.getBusinessId());
        }
        List<TShopCoursesStartingModel> list = tShopCoursesStartingService.selectTShopCoursesStartingList(tShopCoursesStartingModel);

        return getDataTable(list);
    }

    /**
     * 新增开机推荐
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
     * 新增保存开机推荐
     */
    @RequiresPermissions("shop:courses:starting:add")
    @Log(title = "开机推荐", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增开机推荐信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult addSave(TShopCoursesStarting tShopCoursesStarting)
    {
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesById(tShopCoursesStarting.getCoursesId());
        TShop tShop = tShopService.selectTShopById(tShopCourses.getShopId());
        TShopCoursesStartingModel tShopCoursesStartingModel = new TShopCoursesStartingModel();
        tShopCoursesStartingModel.setRegionId(tShop.getRegionId());
        List<TShopCoursesStartingModel> list = tShopCoursesStartingService.selectTShopCoursesStartingList(tShopCoursesStartingModel);
        if(list != null && list.size() > 0){
            return AjaxResult.error("该地区已有推荐。");
        }
        int count = tShopCoursesStartingService.insertTShopCoursesStarting(tShopCoursesStarting);
        if (count == 1) {
            return AjaxResult.success("开机推荐成功。",count);
        } else {
            return AjaxResult.error("开机推荐失败。");
        }

    }

    /**
     * 修改开机推荐
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
        TShopCoursesStarting tShopCoursesStarting = tShopCoursesStartingService.selectTShopCoursesStartingById(id);
        mmap.put("tShopCoursesStarting", tShopCoursesStarting);
        return prefix + "/edit";
    }

    /**
     * 修改保存开机推荐
     */
    @RequiresPermissions("shop:courses:starting:edit")
    @Log(title = "开机推荐", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改开机推荐信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult editSave(TShopCoursesStarting tShopCoursesStarting)
    {
        return toAjax(tShopCoursesStartingService.updateTShopCoursesStarting(tShopCoursesStarting));
    }


    /**
     * 删除开机推荐
     */
    @RequiresPermissions("shop:courses:starting:remove")
    @Log(title = "开机推荐", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除开机推荐信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesStartingService.deleteTShopCoursesStartingByIds(ids));
    }
}
