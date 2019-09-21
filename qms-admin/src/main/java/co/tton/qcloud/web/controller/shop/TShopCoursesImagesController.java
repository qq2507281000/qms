package co.tton.qcloud.web.controller.shop;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.service.ITShopCoursesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;

import javax.annotation.Resource;

/**
 * 课程图片Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/images")
@Api(tags = "课程图片信息")
@Repository
public class TShopCoursesImagesController extends BaseController
{
    private String prefix = "shop/images";

    @Autowired
    private ITShopCoursesImagesService tShopCoursesImagesService;

    @Autowired
    private ITShopCoursesService tShopCoursesService;


    @RequiresPermissions("shop:images:view")
    @GetMapping()
    public String images(@RequestParam(value = "shop-id",required = false) String shopId, ModelMap mmap)
    {
        mmap.put("shopId", "");
        if(StringUtils.isNotEmpty(shopId)) {
            mmap.put("shopId", shopId);
        }
        return prefix + "/images";
    }

    /**
     * 查询课程图片列表
     */
    @RequiresPermissions("shop:images:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("查询课程图片信息")
    public TableDataInfo list(@RequestParam(value="shop-id",required = false)String shopId,TShopCoursesImages tShopCoursesImages)
    {
        startPage();
        if(StringUtils.isNotEmpty(shopId)){
            tShopCoursesImages.setCoursesId(shopId);
        }
        List<TShopCoursesImages> list = tShopCoursesImagesService.selectTShopCoursesImagesList(tShopCoursesImages);
        return getDataTable(list);
    }

    /**
     * 导出课程图片列表
     */
    @RequiresPermissions("shop:images:export")
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
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesByShopId(id);
        TShopCoursesImages tShopCoursesImages = new TShopCoursesImages();
        tShopCoursesImages.setCoursesId(id);
        tShopCoursesImages.setShopId(tShopCourses.getShopId());
        mmap.put("tShopCoursesImages", tShopCoursesImages);
        return prefix + "/add";
    }

    /**
     * 新增保存课程图片
     */
    @RequiresPermissions("shop:images:add")
    @Log(title = "课程图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增课程图片信息")
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
    @RequiresPermissions("shop:images:edit")
    @Log(title = "课程图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改课程图片信息")
    public AjaxResult editSave(TShopCoursesImages tShopCoursesImages)
    {
        return toAjax(tShopCoursesImagesService.updateTShopCoursesImages(tShopCoursesImages));
    }

    /**
     * 删除课程图片
     */
    @RequiresPermissions("shop:images:remove")
    @Log(title = "课程图片", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除课程图片信息")
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesImagesService.deleteTShopCoursesImagesByIds(ids));
    }
}
