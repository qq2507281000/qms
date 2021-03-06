package co.tton.qcloud.web.controller.shop;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.constant.UserConstants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.shiro.service.SysPasswordService;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.domain.TCategory;
import co.tton.qcloud.system.service.ISysUserService;
import co.tton.qcloud.system.service.ITCategoryService;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.service.ITShopService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商家信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Api(value = "商家信息",tags="商家信息")
@Controller
@RequestMapping("/shop")
public class TShopController extends BaseController
{
    private String prefix = "shop/base";

    @Autowired
    private ITShopService tShopService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ITCategoryService categoryService;

    @RequiresPermissions("shop:view")
    @GetMapping()
    public String shop()
    {
        return prefix + "/shop";
    }

    /**
     * 查询商家信息列表
     */
    @ApiOperation("获取商家列表")
    @RequiresPermissions("shop:list")
    @PostMapping("/list")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public TableDataInfo list(TShop tShop)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            tShop.setId(user.getBusinessId());
        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShop.setRegionId(user.getBusinessId());
        }
        else{
            if(StringUtils.isNotEmpty(tShop.getId())){
                tShop.setId(tShop.getId());
            }
        }
        List<TShop> list = tShopService.selectTShopList(tShop);
        return getDataTable(list);
    }

    /**
     * 新增商家信息
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
                mmap.put("regionId",regionId);
            }
//            else{
//                list = tBannerService.selectTBannerList(tBanner);
//            }
        }

        TCategory cate = new TCategory();
        cate.setFlag(Constants.DATA_NORMAL);
        List<TCategory> categories = new ArrayList<>();
        categories = categoryService.selectTCategoryList(cate).stream()
                .filter(d->StringUtils.isNotEmpty(d.getParentId()))
                .sorted(Comparator.comparing(TCategory::getName))
                .collect(Collectors.toList());
        mmap.put("categories",categories);

        return prefix + "/add";
    }

    /**
     * 新增保存商家信息
     */
//    @ApiOperation("新增商家信息")
//    @RequiresPermissions("shop:add")
//    @Log(title = "商家信息", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    @ResponseBody
//    @RoleScope(roleDefined={"ADMIN","SHOP"})
//    public AjaxResult addSave(TShop tShop)
//    {
//        try {
//            SysUser user = ShiroUtils.getSysUser();
//            String id = StringUtils.genericId();
//            tShop.setId(id);
//
//            if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
//                tShop.setId(user.getShopId());
//            }
//            tShop.setFlag(Constants.DATA_NORMAL);
//            tShop.setCreateTime(new Date());
//            tShop.setCreateBy(user.getUserId().toString());
//            return toAjax(tShopService.insertTShop(tShop));
//
////            if (tShopCourses.getParams().containsKey("file")){
////                //新文件上传
////                MultipartFile file = (MultipartFile)tShopCourses.getParams().get("file");
////                if (file !=null){
////                    String fileName = minioFileService.upload(file);
////                    TShopCoursesImages tShopCoursesImages = new TShopCoursesImages();
////                    tShopCoursesImages.setImageUrl(fileName);
////                    return AjaxResult.success("数据保存成功。");
////                }
////                else {
////                    return AjaxResult.error("未能获取上传文件内容。");
////                }
////            }
////            else {
////                return AjaxResult.error("请选择图片上传。");
////            }
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//            logger.error("保存商家图片时发生异常。",ex);
//            return AjaxResult.error("保存商家图片时发生异常。");
//        }
//    }

    /**
     * 修改商家信息
     */
    @ApiOperation("获取商家详细")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        String category = user.getCategory();
        if(StringUtils.isNotEmpty(category)){
            if(StringUtils.equalsAnyIgnoreCase(category,"REGION")){
                String regionId = user.getBusinessId();
                mmap.put("regionId",regionId);
            }
        }
        TShop tShop = tShopService.selectTShopById(id);
        String times = tShop.getShopHoursBegin() + " - " + tShop.getShopHoursEnd();
        Map<String,Object> map = new HashMap<>();
        map.put("times",times);
        tShop.setParams(map);
        mmap.put("tShop", tShop);

        TCategory cate = new TCategory();
        cate.setFlag(Constants.DATA_NORMAL);
        List<TCategory> categories = new ArrayList<>();
        categories = categoryService.selectTCategoryList(cate).stream()
                .filter(d->StringUtils.isNotEmpty(d.getParentId()))
                .sorted(Comparator.comparing(TCategory::getName))
                .collect(Collectors.toList());
        mmap.put("categories",categories);

        return prefix + "/edit";
    }

    /**
     * 修改保存商家信息
     */
    @ApiOperation("更新商家信息")
    @RequiresPermissions("shop:edit")
    @Log(title = "商家信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult editSave(TShop tShop)
    {
        SysUser user = ShiroUtils.getSysUser();
        String times = tShop.getParams().get("times").toString();
//        int suggest = Integer.parseInt(tShop.getParams().getOrDefault("suggest",0).toString());
        int suggest = 0;
        if(StringUtils.isNotEmpty(times)){
            String[] ts = StringUtils.split(times,"-");
            tShop.setShopHoursBegin(ts[0].trim());
            tShop.setShopHoursEnd(ts[1].trim());
        }
        tShop.setSuggest(suggest);

        SysUser shopUser = userService.selectUserByBusinessId(tShop.getId());
        if(shopUser != null){
            if(!StrUtil.equals(shopUser.getPhonenumber(),tShop.getMobile())){
                shopUser.setPhonenumber(tShop.getMobile());
                shopUser.setStatus("1"); // 未认证
                shopUser.setOpenId("");  // 清空OpenId
                userService.updateUser(shopUser);
            }
        }

        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            if(user.getBusinessId().equals(tShop.getId())){
                return toAjax(tShopService.updateTShop(tShop));
            }else{
                return error("不能修改其他商家信息。");
            }
        }
        return toAjax(tShopService.updateTShop(tShop));
    }

    /**
     * 删除商家信息
     */
    @ApiOperation("删除商家信息")
    @RequiresPermissions("shop:remove")
    @Log(title = "商家信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopService.deleteTShopByIds(ids));
    }

    /**
     * 新增保存商家信息
     */
    @ApiOperation("新增商家信息")
    @RequiresPermissions("shop:add")
    @Log(title = "商家信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult addSave(TShop tShop)
    {
        try {
            SysUser user = ShiroUtils.getSysUser();
            String id = StringUtils.genericId();
            tShop.setId(id);
            String times = tShop.getParams().get("times").toString();
            if(StringUtils.isNotEmpty(times)){
                String[] ts = StringUtils.split(times,"-");
                tShop.setShopHoursBegin(ts[0].trim());
                tShop.setShopHoursEnd(ts[1].trim());
            }
            tShop.setSortKey(1);
            tShop.setLevel(1);
            tShop.setStars(Long.parseLong("0"));
            tShop.setWechatShow(0);
            tShop.setAvailable(0);
            tShop.setFlag(Constants.DATA_NORMAL);
            tShop.setCreateTime(new Date());
            tShop.setCreateBy(user.getUserId().toString());
//            int suggest = Integer.parseInt(tShop.getParams().getOrDefault("suggest",0).toString());
            int suggest = 0;
            tShop.setSuggest(suggest);
            //创建商家用户
            SysUser sysUser = new SysUser();
            sysUser.setLoginName(RandomStringUtils.randomAlphanumeric(6));
            sysUser.setUserName(tShop.getName());
            sysUser.setPhonenumber(tShop.getMobile());
            sysUser.setPassword(String.valueOf((int)((Math.random()*9+1)*100000)));
            if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(sysUser)))
            {
                return error("新增商家'" + sysUser.getUserName() + "'失败，手机号码已存在");
            }
            sysUser.setSalt(ShiroUtils.randomSalt());
            sysUser.setPassword(passwordService.encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
            sysUser.setCreateBy(ShiroUtils.getLoginName());
            sysUser.setSex("0");
            sysUser.setBusinessId(tShop.getId());
            sysUser.setCategory("SHOP");
            sysUser.setStatus("1");
            //保存用户
            userService.insertUser(sysUser);
            //保存商家
            tShopService.insertTShop(tShop);

            return AjaxResult.success("商家信息保存成功。",tShop);
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error("保存商家信息时发生异常。",ex);
            return AjaxResult.error("保存商家信息时发生异常。");
        }
    }
}
