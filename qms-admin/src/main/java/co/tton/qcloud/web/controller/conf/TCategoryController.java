package co.tton.qcloud.web.controller.conf;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.web.minio.MinioFileService;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private MinioFileService minioFileService;

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
    public List<TCategory> list(TCategory tCategory)
    {
//        startPage();
        List<TCategory> list = tCategoryService.selectTCategoryList(tCategory);
//        return getDataTable(list);
        return list;
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
        try {
            SysUser currentUser = ShiroUtils.getSysUser();

            String id = StringUtils.genericId();
            tCategory.setId(id);

            if (tCategory.getParams().containsKey("file")){
                MultipartFile file = (MultipartFile)tCategory.getParams().get("file");
                if (file != null){
                    String fileName = minioFileService.upload(file);
                    tCategory.setIcon(fileName);

                    if (tCategory.getParams().containsKey("availableText")){
                        String avaiableText = tCategory.getParams().get("availableText").toString();
                        int avaiable = StrUtil.equalsIgnoreCase(avaiableText,"on")?1:0;
                        tCategory.setAvailable(avaiable);
                    }
                    tCategory.setFlag(Constants.DATA_NORMAL);
                    tCategory.setCreateBy(currentUser.getUserId().toString());
                    tCategory.setCreateTime(DateUtil.date());
                    tCategoryService.insertTCategory(tCategory);
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
            logger.error("保存课程分类图片时发生异常。",ex);
            return AjaxResult.error("保存课程分类图片时发生异常。");
        }
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
        try {
            SysUser currentUser = ShiroUtils.getSysUser();

            if (tCategory.getParams().containsKey("file")){
                if (tCategory.getParams().get("file") != null && !"undefined".equals(tCategory.getParams().get("file"))) {
                    MultipartFile file = (MultipartFile)tCategory.getParams().get("file");
                    if (file != null){
                        String fileName = minioFileService.upload(file);
                        tCategory.setIcon(fileName);
                        if (tCategory.getParams().containsKey("availableText")){
                            String avaiableText = tCategory.getParams().get("availableText").toString();
                            int avaiable = StrUtil.equalsIgnoreCase(avaiableText,"on")?1:0;
                            tCategory.setAvailable(avaiable);
                        }
                    } else {
                        return AjaxResult.error("未能获取上传文件内容。");
                    }

                }
            }
            tCategory.setUpdateBy(currentUser.getUserId().toString());
            tCategory.setUpdateTime(DateUtil.date());
            tCategoryService.updateTCategory(tCategory);
            return AjaxResult.success("数据修改成功。");
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error("保存课程分类图片时发生异常。",ex);
            return AjaxResult.error("保存课程分类图片时发生异常。");
        }
//        return toAjax(tCategoryService.updateTCategory(tCategory));
    }

    /**
     * 删除分类基础
     */
    @RequiresPermissions("conf:category:remove")
    @Log(title = "分类基础", businessType = BusinessType.DELETE)
    @GetMapping( "/remove/{ids}")
    @ResponseBody
    @ApiOperation("删除课程分类信息")
    public AjaxResult remove(String ids)
    {
        return toAjax(tCategoryService.deleteTCategoryByIds(ids));
    }
}
