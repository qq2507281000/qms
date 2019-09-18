package co.tton.qcloud.web.controller.conf;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import co.tton.qcloud.system.domain.TCategory;
import co.tton.qcloud.system.service.ITCategoryService;

/**
 * 分类基础Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/conf/category")
@Api(tags = "课程分类信息")
public class TCategoryController extends BaseController
{
    /***
     * 前端页面地址
     */
    private String prefix = "conf/category";

    @Autowired
    private ITCategoryService tCategoryService;

    @RequiresPermissions("conf:category:view")
    @GetMapping()
    public String category()
    {
        return prefix + "/list";
    }

    /**
     * 查询分类基础列表
     */
    @RequiresPermissions("conf:category:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("查询课程分类信息")
    public TableDataInfo list(TCategory tCategory)
    {
        startPage();
        List<TCategory> list = tCategoryService.selectTCategoryList(tCategory);
        return getDataTable(list);
    }

    /**
     * 导出分类基础列表
     */
    @RequiresPermissions("conf:category:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TCategory tCategory)
    {
        List<TCategory> list = tCategoryService.selectTCategoryList(tCategory);
        ExcelUtil<TCategory> util = new ExcelUtil<TCategory>(TCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 新增分类基础
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存分类基础
     */
    @RequiresPermissions("conf:category:add")
    @Log(title = "分类基础", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增课程分类信息")
    public AjaxResult addSave(TCategory tCategory)
    {
        return toAjax(tCategoryService.insertTCategory(tCategory));
    }

    /**
     * 修改分类基础
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TCategory tCategory = tCategoryService.selectTCategoryById(id);
        mmap.put("tCategory", tCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存分类基础
     */
    @RequiresPermissions("conf:category:edit")
    @Log(title = "分类基础", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改课程分类信息")
    public AjaxResult editSave(TCategory tCategory)
    {
        return toAjax(tCategoryService.updateTCategory(tCategory));
    }

    /**
     * 删除分类基础
     */
    @RequiresPermissions("conf:category:remove")
    @Log(title = "分类基础", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除课程分类信息")
    public AjaxResult remove(String ids)
    {
        return toAjax(tCategoryService.deleteTCategoryByIds(ids));
    }
}
