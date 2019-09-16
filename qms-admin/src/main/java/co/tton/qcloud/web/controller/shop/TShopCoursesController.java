package co.tton.qcloud.web.controller.shop;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.service.ITShopCoursesService;

/**
 * 课程基本信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses/basic")
public class TShopCoursesController extends BaseController
{
    private String prefix = "shop/basic";

    @Autowired
    private ITShopCoursesService tShopCoursesService;

    @RequiresPermissions("shop:courses:basic:view")
    @GetMapping()
    public String courses()
    {
        return prefix + "/basic";
    }

    /**
     * 查询课程基本信息列表
     */
    @RequiresPermissions("shop:courses:basic:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TShopCourses tShopCourses)
    {
        startPage();
        List<TShopCourses> list = tShopCoursesService.selectTShopCoursesList(tShopCourses);
        return getDataTable(list);
    }

    /**
     * 导出课程基本信息列表
     */
    @RequiresPermissions("shop:courses:basic:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TShopCourses tShopCourses)
    {
        List<TShopCourses> list = tShopCoursesService.selectTShopCoursesList(tShopCourses);
        ExcelUtil<TShopCourses> util = new ExcelUtil<TShopCourses>(TShopCourses.class);
        return util.exportExcel(list, "courses");
    }

    /**
     * 新增课程基本信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存课程基本信息
     */
    @RequiresPermissions("shop:courses:basic:add")
    @Log(title = "课程基本信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TShopCourses tShopCourses)
    {
        return toAjax(tShopCoursesService.insertTShopCourses(tShopCourses));
    }

    /**
     * 修改课程基本信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesById(id);
        mmap.put("tShopCourses", tShopCourses);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程基本信息
     */
    @RequiresPermissions("shop:courses:basic:edit")
    @Log(title = "课程基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TShopCourses tShopCourses)
    {
        return toAjax(tShopCoursesService.updateTShopCourses(tShopCourses));
    }

    /**
     * 删除课程基本信息
     */
    @RequiresPermissions("shop:courses:basic:remove")
    @Log(title = "课程基本信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesService.deleteTShopCoursesByIds(ids));
    }
}
