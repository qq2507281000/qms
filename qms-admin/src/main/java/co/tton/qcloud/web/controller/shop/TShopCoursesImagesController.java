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
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;

/**
 * 课程图片Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses/images")
public class TShopCoursesImagesController extends BaseController
{
    private String prefix = "shop/images";

    @Autowired
    private ITShopCoursesImagesService tShopCoursesImagesService;

    @RequiresPermissions("shop:courses:images:view")
    @GetMapping()
    public String images()
    {
        return prefix + "/images";
    }

    /**
     * 查询课程图片列表
     */
    @RequiresPermissions("shop:courses:images:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TShopCoursesImages tShopCoursesImages)
    {
        startPage();
        List<TShopCoursesImages> list = tShopCoursesImagesService.selectTShopCoursesImagesList(tShopCoursesImages);
        return getDataTable(list);
    }

    /**
     * 导出课程图片列表
     */
    @RequiresPermissions("shop:courses:images:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TShopCoursesImages tShopCoursesImages)
    {
        List<TShopCoursesImages> list = tShopCoursesImagesService.selectTShopCoursesImagesList(tShopCoursesImages);
        ExcelUtil<TShopCoursesImages> util = new ExcelUtil<TShopCoursesImages>(TShopCoursesImages.class);
        return util.exportExcel(list, "images");
    }

    /**
     * 新增课程图片
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存课程图片
     */
    @RequiresPermissions("shop:courses:images:add")
    @Log(title = "课程图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TShopCoursesImages tShopCoursesImages)
    {
        return toAjax(tShopCoursesImagesService.insertTShopCoursesImages(tShopCoursesImages));
    }

    /**
     * 修改课程图片
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCoursesImages tShopCoursesImages = tShopCoursesImagesService.selectTShopCoursesImagesById(id);
        mmap.put("tShopCoursesImages", tShopCoursesImages);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程图片
     */
    @RequiresPermissions("shop:courses:images:edit")
    @Log(title = "课程图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TShopCoursesImages tShopCoursesImages)
    {
        return toAjax(tShopCoursesImagesService.updateTShopCoursesImages(tShopCoursesImages));
    }

    /**
     * 删除课程图片
     */
    @RequiresPermissions("shop:courses:images:remove")
    @Log(title = "课程图片", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesImagesService.deleteTShopCoursesImagesByIds(ids));
    }
}
