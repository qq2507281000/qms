package co.tton.qcloud.web.controller.shop;

import java.io.IOException;
import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import co.tton.qcloud.system.domain.TShopCourses;
import co.tton.qcloud.system.service.ITShopCoursesService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 课程基本信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses")
@Api(tags = "课程基本信息")
public class TShopCoursesController extends BaseController
{
    private String prefix = "shop/courses";

    @Autowired
    private ITShopCoursesService tShopCoursesService;

    @Autowired
    private MinioFileService minioFileService;

    @RequiresPermissions("shop:courses:view")
    @GetMapping()
    public String courses(@RequestParam(value = "shop-id",required = false) String shopId, ModelMap mmap)
    {
        mmap.put("shopId", "");
        if(StringUtils.isNotEmpty(shopId)) {
            mmap.put("shopId", shopId);
        }
        return prefix + "/list";
    }

    /**
     * 查询课程基本信息列表
     */
    @RequiresPermissions("shop:courses:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取课程基本信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public TableDataInfo list(@RequestParam(value="shop-id",required = false)String shopId, @ApiParam("课程基本信息实体对象")TShopCourses tShopCourses)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            tShopCourses.setShopId(user.getShopId());
        }
        else{
            if(StringUtils.isNotEmpty(shopId)){
                tShopCourses.setShopId(shopId);
            }
        }
        List<TShopCourses> list = tShopCoursesService.selectTShopCoursesList(tShopCourses);
        return getDataTable(list);
    }

    /**
     * 导出课程基本信息列表
     */
    @RequiresPermissions("shop:courses:export")
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
    @RequiresPermissions("shop:courses:add")
    @Log(title = "课程基本信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增课程基本信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult addSave(TShopCourses tShopCourses) throws IOException {
        try {
            SysUser currentUser = ShiroUtils.getSysUser();
            String id = StringUtils.genericId();
            tShopCourses.setId(id);

            if (tShopCourses.getParams().containsKey("file")){
                //新文件上传
                MultipartFile file = (MultipartFile)tShopCourses.getParams().get("file");
                if (file !=null){
                    String fileName = minioFileService.upload(file);
                    TShopCoursesImages tShopCoursesImages = new TShopCoursesImages();
                    tShopCoursesImages.setImageUrl(fileName);
                    return AjaxResult.success("数据保存成功。");
                }
                else {
                    return AjaxResult.error("未能获取上传文件内容。");
                }
            }
            else {
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
    @RequiresPermissions("shop:courses:edit")
    @Log(title = "课程基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改课程基本信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult editSave(TShopCourses tShopCourses)
    {
        return toAjax(tShopCoursesService.updateTShopCourses(tShopCourses));
    }

    /**
     * 删除课程基本信息
     */
    @RequiresPermissions("shop:courses:remove")
    @Log(title = "课程基本信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除课程基本信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesService.deleteTShopCoursesByIds(ids));
    }
}
