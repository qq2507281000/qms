package co.tton.qcloud.web.controller.shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
    private ITShopService tShopService;

    @Autowired
    private MinioFileService minioFileService;

    @Autowired
    private ITShopCoursesImagesService shopCoursesImagesService;

    @Autowired
    private ITCategoryService categoryService;

    @Autowired
    private ITShopCoursesPriceService tShopCoursesPriceService;

    @RequiresPermissions("shop:courses:view")
    @GetMapping()
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public String courses(@RequestParam(value = "shop-id",required = false) String shopId, ModelMap mmap)
    {
        mmap.put("shopId", "");
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            mmap.put("shopId",user.getBusinessId());
        }
        else{
            if(StringUtils.isNotEmpty(shopId)){
                mmap.put("shopId", shopId);
            }
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
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public TableDataInfo list(@RequestParam(value="shop-id",required = false)String shopId, @ApiParam("课程基本信息实体对象")TShopCourses tShopCourses)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            tShopCourses.setShopId(user.getBusinessId());
        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShopCourses.setRegionId(user.getBusinessId());
        }
        if(StringUtils.isNotEmpty(shopId)){
                tShopCourses.setShopId(shopId);
        }
        //商户推荐状态进入了筛选项,在这清空
        if(tShopCourses != null) {
            tShopCourses.setSuggest(null);
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
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public String add(@RequestParam(value="shop-id",required = false)String shopId,ModelMap mmap)
    {
        TShop shop = new TShop();
        shop.setFlag(Constants.DATA_NORMAL);
        SysUser user = ShiroUtils.getSysUser();
        List<TShop> list = new ArrayList<>();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"ADMIN")){

        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            shop.setId(user.getBusinessId());
        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            shop.setRegionId(user.getBusinessId());
        }
        list = tShopService.selectTShopList(shop);
        mmap.put("shop",list);
        TCategory category = new TCategory();
        category.setFlag(Constants.DATA_NORMAL);
        List<TCategory> categories = new ArrayList<>();
        categories = categoryService.selectTCategoryList(category).stream().filter(d->StringUtils.isNotEmpty(d.getParentId())).collect(Collectors.toList());
        mmap.put("categories",categories);

        return prefix + "/add";
    }

    /**
     * 新增课程基本信息
     */
    @GetMapping("/add/{shopId}")
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public String addId(@PathVariable("shopId") String shopId,ModelMap mmap)
    {
        TShop shop = new TShop();
        shop.setFlag(Constants.DATA_NORMAL);
        SysUser user = ShiroUtils.getSysUser();
        List<TShop> list = new ArrayList<>();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"ADMIN")){

        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            shop.setId(user.getBusinessId());
        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            shop.setRegionId(user.getBusinessId());
        }
        list = tShopService.selectTShopList(shop);
        mmap.put("shop",list);
        mmap.put("shopId",new String(shopId));
        TCategory category = new TCategory();
        category.setFlag(Constants.DATA_NORMAL);
        List<TCategory> categories = new ArrayList<>();
        categories = categoryService.selectTCategoryList(category).stream().filter(d->StringUtils.isNotEmpty(d.getParentId())).collect(Collectors.toList());
        mmap.put("categories",categories);

        return prefix + "/add";
    }

//    /**
//     * 新增保存课程基本信息
//     */
//    @RequiresPermissions("shop:courses:add")
//    @Log(title = "课程基本信息", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    @ResponseBody
//    @ApiOperation("新增课程基本信息")
//    @RoleScope(roleDefined={"ADMIN","SHOP"})
//    public AjaxResult addSave(TShopCourses tShopCourses) throws IOException {
//        try {
//            SysUser user = ShiroUtils.getSysUser();
//            String id = StringUtils.genericId();
//            tShopCourses.setId(id);
//            if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
//                tShopCourses.setShopId(user.getBusinessId());
//            }
//            String shopId = tShopCourses.getShopId();
////            tShopCourses.setShopId(shopId);
////            tShopCourses.setShopName(shopName);
//            tShopCourses.setFlag(Constants.DATA_NORMAL);
//            tShopCourses.setCreateTime(new Date());
//            tShopCourses.setCreateBy(user.getUserId().toString());
//            int result = tShopCoursesService.insertTShopCourses(tShopCourses);
//            if(tShopCourses.getParams().containsKey("img")){
//                String img = tShopCourses.getParams().get("img").toString();
//                String[] imgs = StringUtils.split(img,"|");
//                for (String im:imgs) {
//                    TShopCoursesImages cimg = new TShopCoursesImages();
//                    cimg.setId(StringUtils.genericId());
//                    cimg.setCoursesId(tShopCourses.getId());
//                    cimg.setShopId(shopId);
//                    cimg.setImageUrl(im);
//                    cimg.setFlag(Constants.DATA_NORMAL);
//                    cimg.setSortKey(0);
//                    cimg.setCreateTime(DateUtils.getNowDate());
//                    cimg.setCreateBy(user.getUserId().toString());
//                    shopCoursesImagesService.insertTShopCoursesImages(cimg);
//                }
//            }
//            if(result == 1){
////                return success("保存成功。");
//                return AjaxResult.success("保存成功。",tShopCourses);
//            }
//            else{
//                return error("保存失败。");
//            }
//        }
//        catch (Exception ex){
//            ex.printStackTrace();
//            logger.error("保存课程图片时发生异常。",ex);
//            return AjaxResult.error("保存课程图片时发生异常。");
//        }
//    }
    /**
     * 新增保存课程基本信息
     */
    @RequiresPermissions("shop:courses:add")
    @Log(title = "课程基本信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增课程基本信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult addSave(TShopCoursesPriceModel tShopCoursesPriceModel) throws IOException {
        try {
            SysUser user = ShiroUtils.getSysUser();
            String id = StringUtils.genericId();
            TShopCourses tShopCourses = new TShopCourses();
            tShopCourses.setId(id);
            if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
                tShopCoursesPriceModel.setShopId(user.getBusinessId());
            }
            String shopId = tShopCoursesPriceModel.getShopId();
//            tShopCourses.setShopId(shopId);
//            tShopCourses.setShopName(shopName);
            tShopCourses.setShopId(shopId);
            tShopCourses.setTitle(tShopCoursesPriceModel.getTitle());
            tShopCourses.setSubTitle(tShopCoursesPriceModel.getSubTitle());
            tShopCourses.setContentHtml(tShopCoursesPriceModel.getContentHtml());
            tShopCourses.setSuggest(tShopCoursesPriceModel.getSuggest());
            tShopCourses.setSalesCount(tShopCoursesPriceModel.getSalesCount());
            tShopCourses.setAvailable(tShopCoursesPriceModel.getAvailable());
            tShopCourses.setSortKey(tShopCoursesPriceModel.getSortKey());
            tShopCourses.setSku(tShopCoursesPriceModel.getSku());
            tShopCourses.setUseDiscount(tShopCoursesPriceModel.getUseDiscount());
            tShopCourses.setShopName(tShopCoursesPriceModel.getShopName());
            tShopCourses.setCategoryId(tShopCoursesPriceModel.getCategoryId());
            tShopCourses.setRegionName(tShopCoursesPriceModel.getRegionName());
            tShopCourses.setRegionId(tShopCoursesPriceModel.getRegionId());
            tShopCourses.setFlag(Constants.DATA_NORMAL);
            tShopCourses.setCreateTime(new Date());
            tShopCourses.setCreateBy(user.getUserId().toString());
            int result = tShopCoursesService.insertTShopCourses(tShopCourses);
            if(tShopCoursesPriceModel.getParams().containsKey("img")){
                String img = tShopCoursesPriceModel.getParams().get("img").toString();
                String[] imgs = StringUtils.split(img,"|");
                for (String im:imgs) {
                    TShopCoursesImages cimg = new TShopCoursesImages();
                    cimg.setId(StringUtils.genericId());
                    cimg.setCoursesId(tShopCourses.getId());
                    cimg.setShopId(shopId);
                    cimg.setImageUrl(im);
                    cimg.setFlag(Constants.DATA_NORMAL);
                    cimg.setSortKey(0);
                    cimg.setCreateTime(DateUtils.getNowDate());
                    cimg.setCreateBy(user.getUserId().toString());
                    shopCoursesImagesService.insertTShopCoursesImages(cimg);
                }
            }
            TShopCoursesPrice tShopCoursesPrice = new TShopCoursesPrice();
            tShopCoursesPrice.setId(StringUtils.genericId());
            tShopCoursesPrice.setShopId(tShopCourses.getShopId());
            tShopCoursesPrice.setCoursesId(tShopCourses.getId());
            tShopCoursesPrice.setPrice(tShopCoursesPriceModel.getPrice());
            tShopCoursesPrice.setPerLimitBuy(tShopCoursesPriceModel.getPerLimitBuy());
            tShopCoursesPrice.setSpecialPriceLevel(tShopCoursesPriceModel.getSpecialPriceLevel());
            tShopCoursesPrice.setSubTitleOne(tShopCoursesPriceModel.getSubTitleOne());
            tShopCoursesPrice.setSubTitleTwo(tShopCoursesPriceModel.getSubTitleTwo());
            tShopCoursesPrice.setSubTitleThree(tShopCoursesPriceModel.getSubTitleThree());
            tShopCoursesPrice.setFlag(Constants.DATA_NORMAL);
            tShopCoursesPrice.setCreateTime(new Date());
            tShopCoursesPrice.setCreateBy(user.getUserId().toString());
            tShopCoursesPriceService.insertTShopCoursesPrice(tShopCoursesPrice);
            if(result == 1){
//                return success("保存成功。");
                return AjaxResult.success("保存成功。",tShopCourses);
            }
            else{
                return error("保存失败。");
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
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesById(id);
        mmap.put("tShopCourses", tShopCourses);
        TShopCoursesImages query = new TShopCoursesImages();
        query.setCoursesId(id);

        List<TShopCoursesImages> images = shopCoursesImagesService.selectTShopCoursesImagesList(query);
        mmap.put("coursesImages", JSON.toJSON(images));


        List<TShopCoursesPrice> tShopCoursesPriceList = tShopCoursesPriceService.getCoursesPriceById(id);
        TShopCoursesPrice tShopCoursesPrice = null;
        if (tShopCoursesPriceList != null) {
            tShopCoursesPrice = tShopCoursesPriceList.get(0);
        }

        TShopCoursesPriceModel tShopCoursesPriceModel = new TShopCoursesPriceModel();
        tShopCoursesPriceModel.setId(tShopCourses.getId());
        tShopCoursesPriceModel.setShopId(tShopCourses.getShopId());
        tShopCoursesPriceModel.setTitle(tShopCourses.getTitle());
        tShopCoursesPriceModel.setSubTitle(tShopCourses.getSubTitle());
        tShopCoursesPriceModel.setCategoryId(tShopCourses.getCategoryId());
        tShopCoursesPriceModel.setSalesCount(tShopCourses.getSalesCount());
        tShopCoursesPriceModel.setSortKey(tShopCourses.getSortKey());
        tShopCoursesPriceModel.setSuggest(tShopCourses.getSuggest());
        tShopCoursesPriceModel.setAvailable(tShopCourses.getAvailable());
        tShopCoursesPriceModel.setUseDiscount(tShopCourses.getUseDiscount());
        tShopCoursesPriceModel.setContentHtml(tShopCourses.getContentHtml());
        tShopCoursesPriceModel.setPriceId(tShopCoursesPrice.getId());
        tShopCoursesPriceModel.setPrice(tShopCoursesPrice.getPrice());
        tShopCoursesPriceModel.setPerLimitBuy(tShopCoursesPrice.getPerLimitBuy());
        tShopCoursesPriceModel.setSpecialPriceLevel(tShopCoursesPrice.getSpecialPriceLevel());
        tShopCoursesPriceModel.setSubTitleOne(tShopCoursesPrice.getSubTitleOne());
        tShopCoursesPriceModel.setSubTitleTwo(tShopCoursesPrice.getSubTitleTwo());
        tShopCoursesPriceModel.setSubTitleThree(tShopCoursesPrice.getSubTitleThree());
        mmap.put("tShopCoursesPriceModel", tShopCoursesPriceModel);
        TShop shop = new TShop();
        shop.setFlag(Constants.DATA_NORMAL);
        SysUser user = ShiroUtils.getSysUser();
        List<TShop> list = new ArrayList<>();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"ADMIN")){

        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            shop.setId(user.getBusinessId());
        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            shop.setRegionId(user.getBusinessId());
        }
        list = tShopService.selectTShopList(shop);
        mmap.put("shop",list);

        TCategory category = new TCategory();
        category.setFlag(Constants.DATA_NORMAL);
        List<TCategory> categories = new ArrayList<>();
        categories = categoryService.selectTCategoryList(category).stream().filter(d->StringUtils.isNotEmpty(d.getParentId())).collect(Collectors.toList());
        mmap.put("categories",categories);

        return prefix + "/edit";
    }

//    /**
//     * 修改保存课程基本信息
//     */
//    @RequiresPermissions("shop:courses:edit")
//    @Log(title = "课程基本信息", businessType = BusinessType.UPDATE)
//    @PostMapping("/edit")
//    @ResponseBody
//    @ApiOperation("修改课程基本信息")
//    @RoleScope(roleDefined={"ADMIN","SHOP"})
//    public AjaxResult editSave(TShopCourses tShopCourses)
//    {
//        return toAjax(tShopCoursesService.updateTShopCourses(tShopCourses));
//    }

    /**
     * 修改保存课程基本信息
     */
    @RequiresPermissions("shop:courses:edit")
    @Log(title = "课程基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改课程基本信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult editSave(TShopCoursesPriceModel tShopCoursesPriceModel)
    {
        TShopCourses tShopCourses = new TShopCourses();
        tShopCourses.setId(tShopCoursesPriceModel.getId());
        String shopId = tShopCoursesPriceModel.getShopId();
//            tShopCourses.setShopId(shopId);
//            tShopCourses.setShopName(shopName);
        tShopCourses.setShopId(shopId);
        tShopCourses.setTitle(tShopCoursesPriceModel.getTitle());
        tShopCourses.setSubTitle(tShopCoursesPriceModel.getSubTitle());
        tShopCourses.setContentHtml(tShopCoursesPriceModel.getContentHtml());
        tShopCourses.setSuggest(tShopCoursesPriceModel.getSuggest());
        tShopCourses.setSalesCount(tShopCoursesPriceModel.getSalesCount());
        tShopCourses.setAvailable(tShopCoursesPriceModel.getAvailable());
        tShopCourses.setSortKey(tShopCoursesPriceModel.getSortKey());
        tShopCourses.setSku(tShopCoursesPriceModel.getSku());
        tShopCourses.setUseDiscount(tShopCoursesPriceModel.getUseDiscount());
        tShopCourses.setShopName(tShopCoursesPriceModel.getShopName());
        tShopCourses.setCategoryId(tShopCoursesPriceModel.getCategoryId());
        tShopCourses.setRegionName(tShopCoursesPriceModel.getRegionName());
        tShopCourses.setRegionId(tShopCoursesPriceModel.getRegionId());
        tShopCourses.setFlag(Constants.DATA_NORMAL);
        tShopCourses.setUpdateTime(new Date());
        int result = tShopCoursesService.updateTShopCourses(tShopCourses);
        if(tShopCoursesPriceModel.getParams().containsKey("img")){
            String img = tShopCoursesPriceModel.getParams().get("img").toString();
            String[] imgs = StringUtils.split(img,"|");
            for (String im:imgs) {
                TShopCoursesImages cimg = new TShopCoursesImages();
                cimg.setId(StringUtils.genericId());
                cimg.setCoursesId(tShopCourses.getId());
                cimg.setShopId(shopId);
                cimg.setImageUrl(im);
                cimg.setFlag(Constants.DATA_NORMAL);
                cimg.setSortKey(0);
                cimg.setCreateTime(DateUtils.getNowDate());
                shopCoursesImagesService.insertTShopCoursesImages(cimg);
            }
        }
        TShopCoursesPrice tShopCoursesPrice = new TShopCoursesPrice();
        tShopCoursesPrice.setId(tShopCoursesPriceModel.getPriceId());
        tShopCoursesPrice.setShopId(tShopCourses.getShopId());
        tShopCoursesPrice.setCoursesId(tShopCourses.getId());
        tShopCoursesPrice.setPrice(tShopCoursesPriceModel.getPrice());
        tShopCoursesPrice.setPerLimitBuy(tShopCoursesPriceModel.getPerLimitBuy());
        tShopCoursesPrice.setSpecialPriceLevel(tShopCoursesPriceModel.getSpecialPriceLevel());
        tShopCoursesPrice.setSubTitleOne(tShopCoursesPriceModel.getSubTitleOne());
        tShopCoursesPrice.setSubTitleTwo(tShopCoursesPriceModel.getSubTitleTwo());
        tShopCoursesPrice.setSubTitleThree(tShopCoursesPriceModel.getSubTitleThree());
        tShopCoursesPrice.setFlag(Constants.DATA_NORMAL);
        tShopCoursesPrice.setUpdateTime(new Date());
        tShopCoursesPriceService.updateTShopCoursesPrice(tShopCoursesPrice);
        if(result == 1){
//                return success("保存成功。");
            return AjaxResult.success("修改成功。",tShopCourses);
        }
        else{
            return error("修改失败。");
        }
//        return toAjax(tShopCoursesService.updateTShopCourses(tShopCourses));
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
