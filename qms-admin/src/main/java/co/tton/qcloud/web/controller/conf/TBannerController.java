package co.tton.qcloud.web.controller.conf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.annotation.AllowAdmin;
import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bouncycastle.pqc.math.linearalgebra.IntUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import co.tton.qcloud.system.domain.TBanner;
import co.tton.qcloud.system.service.ITBannerService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页滚动广告Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@ApiOperation("首页滚动广告")
@Controller
@RequestMapping("/conf/banner")
public class TBannerController extends BaseController
{
    /***
     * 前端地址前缀
     */
    private String prefix = "conf/banner";

    @Autowired
    private ITBannerService tBannerService;

    @Autowired
    private MinioFileService minioFileService;

    @RequiresPermissions("conf:banner:view")
    @GetMapping()
    //@RoleScope(roleDefined={"ADMIN"})
    public String banner()
    {
        return prefix + "/list";
    }

    /**
     * 查询首页滚动广告列表
     */
    @ApiOperation("获取滚动广告列表")
    @RequiresPermissions("conf:banner:list")
    @PostMapping("/list")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public TableDataInfo list(TBanner tBanner)
    {
        startPage();
        List<TBanner> list = new ArrayList<>();
        SysUser user = ShiroUtils.getSysUser();
        String category = user.getCategory();
        if(StringUtils.isNotEmpty(category)){
            if(StringUtils.equalsAnyIgnoreCase(category,"SHOP")){
                String shopId = user.getBusinessId();
            }
            else if(StringUtils.equalsAnyIgnoreCase(category,"REGION")){
                String regionId = user.getBusinessId();
                tBanner.setCityId(regionId);
                list = tBannerService.selectTBannerList(tBanner);
            }
            else{
                list = tBannerService.selectTBannerList(tBanner);
            }
        }
        return getDataTable(list);
    }

    /**
     * 新增首页滚动广告
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {

        SysUser user = ShiroUtils.getSysUser();
        String category = user.getCategory();
        if(StringUtils.isNotEmpty(category)){
//            if(StringUtils.equalsAnyIgnoreCase(category,"SHOP")){
//                String shopId = user.getBusinessId();
//            }
//            else
            if(StringUtils.equalsAnyIgnoreCase(category,"REGION")){
                String regionId = user.getBusinessId();
                mmap.put("cityId",regionId);
            }
//            else{
//                list = tBannerService.selectTBannerList(tBanner);
//            }
        }
        return prefix + "/add";
    }

    /**
     * 新增保存首页滚动广告
     */
    @ApiOperation("新增滚动广告")
    @RequiresPermissions("conf:banner:add")
    @Log(title = "首页滚动广告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult addSave(TBanner tBanner)
    {
        try {
            SysUser currentUser = ShiroUtils.getSysUser();
            String id = StringUtils.genericId();
            tBanner.setId(id);
            if(StrUtil.isEmpty(tBanner.getImg())){
                return AjaxResult.error("未能上传图片。");
            }
            tBanner.setFlag(Constants.DATA_NORMAL);
            tBanner.setCreateBy(currentUser.getUserId().toString());
            tBanner.setCreateTime(DateUtil.date());
            return toAjax(tBannerService.insertTBanner(tBanner));
//            return AjaxResult.success("数据保存成功。");
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("保存滚动广告图片时发生异常。",ex);
            return AjaxResult.error("保存滚动广告图片时发生异常。");
        }
    }

    /**
     * 修改首页滚动广告
     */
    @ApiOperation("获取滚动广告详情")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TBanner tBanner = tBannerService.selectTBannerById(id);
        mmap.put("tBanner", tBanner);
        SysUser user = ShiroUtils.getSysUser();
        String category = user.getCategory();
        if(StringUtils.isNotEmpty(category)){
//            if(StringUtils.equalsAnyIgnoreCase(category,"SHOP")){
//                String shopId = user.getBusinessId();
//            }
//            else
            if(StringUtils.equalsAnyIgnoreCase(category,"REGION")){
                String regionId = user.getBusinessId();
                mmap.put("cityId",regionId);
            }
//            else{
//                list = tBannerService.selectTBannerList(tBanner);
//            }
        }
        return prefix + "/edit";
    }

    /**
     * 修改保存首页滚动广告
     */
    @ApiOperation("更新滚动广告")
    @RequiresPermissions("conf:banner:edit")
    @Log(title = "首页滚动广告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult editSave(TBanner tBanner)
    {
        SysUser user = ShiroUtils.getSysUser();
        tBanner.setUpdateBy(user.getUserId().toString());
        tBanner.setUpdateTime(DateUtils.getNowDate());
        return toAjax(tBannerService.updateTBanner(tBanner));
    }

    /**
     * 删除首页滚动广告
     */
    @ApiOperation("删除滚动广告")
    @RequiresPermissions("conf:banner:remove")
    @Log(title = "首页滚动广告", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tBannerService.deleteTBannerByIds(ids));
    }


//    /***
//     * 文件上传
//     * @param file 文件地址
//     * @return
//     */
//    @ApiOperation("滚动广告图片上传")
//    @PostMapping("/img/upload")
//    @Log(title = "滚动广告图片上传", businessType=BusinessType.IMPORT)
//    @RoleScope(roleDefined={"ADMIN"})
//    public AjaxResult upload(@RequestParam("file") MultipartFile file){
//        try{
////            String fileName = MinioFileService.upload(file);
////            return AjaxResult.success(fileName);
//            return AjaxResult.success();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//            logger.error("文件上传发生异常。",ex);
//            return AjaxResult.error("文件上传发生异常。");
//        }
//    }
}
