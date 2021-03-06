package co.tton.qcloud.web.controller.shop;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;
import org.springframework.web.multipart.MultipartFile;

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
public class TShopCoursesImagesController extends BaseController
{
    private String prefix = "shop/images";

    @Autowired
    private ITShopCoursesImagesService tShopCoursesImagesService;

    @Autowired
    private ITShopCoursesService tShopCoursesService;

    @Autowired
    private MinioFileService minioFileService;


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
    @RoleScope(roleDefined={"ADMIN","SHOP"})
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
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesById(id);
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
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult addSave(TShopCoursesImages tShopCoursesImages) {
        try {
            SysUser currentUser = ShiroUtils.getSysUser();

            String id = StringUtils.genericId();
            tShopCoursesImages.setId(id);

            if (tShopCoursesImages.getParams().containsKey("file")) {
                MultipartFile file = (MultipartFile) tShopCoursesImages.getParams().get("file");
                if (file != null) {
                    String fileName = minioFileService.upload(file);
                    tShopCoursesImages.setImageUrl(fileName);

                    tShopCoursesImages.setFlag(Constants.DATA_NORMAL);
                    tShopCoursesImages.setCreateBy(currentUser.getUserId().toString());
                    tShopCoursesImages.setCreateTime(DateUtil.date());
                    tShopCoursesImagesService.insertTShopCoursesImages(tShopCoursesImages);
                    return AjaxResult.success("数据保存成功。");
                }
                else {
                    return AjaxResult.error("未能获取上传文件内容。");
                }
            }
            else{
                return AjaxResult.error("请选择图片上传。");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error("保存课程图片时发生异常。",ex);
            return AjaxResult.error("保存课程图片时发生异常。");
        }
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
    @RoleScope(roleDefined={"ADMIN","SHOP"})
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
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesImagesService.deleteTShopCoursesImagesByIds(ids));
    }

    /**
     * 删除课程图片
     */
    @RequiresPermissions("shop:images:remove")
    @Log(title = "课程图片", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    @ApiOperation("删除课程图片信息")
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public AjaxResult removeById(@PathVariable("id") String id)
    {
        return toAjax(tShopCoursesImagesService.deleteTShopCoursesImagesByIds(id));
    }
}
