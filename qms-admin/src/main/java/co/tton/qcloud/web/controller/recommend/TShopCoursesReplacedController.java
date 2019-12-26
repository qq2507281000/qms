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
 * 最近上新Controller
 *
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses/replaced")
@Api(tags = "最近上新")
public class TShopCoursesReplacedController extends BaseController
{
    private String prefix = "recommend/replaced";

    @Autowired
    private ITShopCoursesReplacedService tShopCoursesReplacedService;

    @Autowired
    private ITShopCoursesService tShopCoursesService;

    @Autowired
    private ITShopService tShopService;

    @RequiresPermissions("shop:courses:replaced:list")
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
     * 查询最近上新列表
     */
    @RequiresPermissions("shop:courses:replaced:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取最近上新信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public TableDataInfo list(TShopCoursesReplacedModel tShopCoursesReplacedModel)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShopCoursesReplacedModel.setRegionId(user.getBusinessId());
        }
        List<TShopCoursesReplacedModel> list = tShopCoursesReplacedService.selectTShopCoursesReplacedList(tShopCoursesReplacedModel);

        return getDataTable(list);
    }

    /**
     * 新增最近上新
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
     * 新增保存最近上新
     */
    @RequiresPermissions("shop:courses:replaced:add")
    @Log(title = "最近上新", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增最近上新信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult addSave(TShopCoursesReplaced tShopCoursesReplaced)
    {
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesById(tShopCoursesReplaced.getCoursesId());
        TShop tShop = tShopService.selectTShopById(tShopCourses.getShopId());
        TShopCoursesReplacedModel tShopCoursesReplacedModel = new TShopCoursesReplacedModel();
        tShopCoursesReplacedModel.setRegionId(tShop.getRegionId());
        List<TShopCoursesReplacedModel> list = tShopCoursesReplacedService.selectTShopCoursesReplacedList(tShopCoursesReplacedModel);
        if(list != null && list.size()>=10){
            return AjaxResult.error("该地区推荐商家已满10个。");
        }
        int count = tShopCoursesReplacedService.insertTShopCoursesReplaced(tShopCoursesReplaced);
        if (count == 1) {
            return AjaxResult.success("最近上新成功。",count);
        } else {
            return AjaxResult.error("最近上新失败。");
        }

    }

    /**
     * 修改最近上新
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
        TShopCoursesReplaced tShopCoursesReplaced = tShopCoursesReplacedService.selectTShopCoursesReplacedById(id);
        mmap.put("tShopCoursesReplaced", tShopCoursesReplaced);
        return prefix + "/edit";
    }

    /**
     * 修改保存最近上新
     */
    @RequiresPermissions("shop:courses:replaced:edit")
    @Log(title = "最近上新", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改最近上新信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult editSave(TShopCoursesReplaced tShopCoursesReplaced)
    {
        return toAjax(tShopCoursesReplacedService.updateTShopCoursesReplaced(tShopCoursesReplaced));
    }


    /**
     * 删除最近上新
     */
    @RequiresPermissions("shop:courses:replaced:remove")
    @Log(title = "最近上新", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除最近上新信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesReplacedService.deleteTShopCoursesReplacedByIds(ids));
    }
}
