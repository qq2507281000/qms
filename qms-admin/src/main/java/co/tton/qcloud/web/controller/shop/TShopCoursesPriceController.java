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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import co.tton.qcloud.system.domain.TShopCoursesPrice;
import co.tton.qcloud.system.service.ITShopCoursesPriceService;

/**
 * 课程价格Controller
 * 
 * @author qcloudF
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/price")
@Api(tags = "课程价格信息")
public class TShopCoursesPriceController extends BaseController
{
    private String prefix = "shop/price";

    @Autowired
    private ITShopCoursesPriceService tShopCoursesPriceService;

    @Autowired
    private ITShopCoursesService tShopCoursesService;


    @RequiresPermissions("shop:price:view")
    @GetMapping()
    public String price(@RequestParam(value = "shop-id",required = false) String shopId, ModelMap mmap)
    {
        mmap.put("shopId", "");
        if(StringUtils.isNotEmpty(shopId)) {
            mmap.put("shopId", shopId);
        }
        return prefix + "/price";
    }

    /**
     * 查询课程价格列表
     */
    @RequiresPermissions("shop:price:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("查询课程价格信息")
    public TableDataInfo list(@RequestParam(value="shop-id",required = false)String shopId,TShopCoursesPrice tShopCoursesPrice)
    {
        startPage();
        if(StringUtils.isNotEmpty(shopId)){
            tShopCoursesPrice.setCoursesId(shopId);
        }
        List<TShopCoursesPrice> list = tShopCoursesPriceService.selectTShopCoursesPriceList(tShopCoursesPrice);
        return getDataTable(list);
    }

    /**
     * 导出课程价格列表
     */
    @RequiresPermissions("shop:price:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TShopCoursesPrice tShopCoursesPrice)
    {
        List<TShopCoursesPrice> list = tShopCoursesPriceService.selectTShopCoursesPriceList(tShopCoursesPrice);
        ExcelUtil<TShopCoursesPrice> util = new ExcelUtil<TShopCoursesPrice>(TShopCoursesPrice.class);
        return util.exportExcel(list, "price");
    }

    /**
     * 新增课程价格
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCourses tShopCourses = tShopCoursesService.selectTShopCoursesById(id);
        TShopCoursesPrice tShopCoursesPrice = new TShopCoursesPrice();
        tShopCoursesPrice.setCoursesId(id);
        tShopCoursesPrice.setShopId(tShopCourses.getShopId());
        mmap.put("tShopCoursesPrice", tShopCoursesPrice);
        return prefix + "/add";
    }

    /**
     * 新增保存课程价格
     */
    @RequiresPermissions("shop:price:add")
    @Log(title = "课程价格", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增课程价格信息")
    public AjaxResult addSave(TShopCoursesPrice tShopCoursesPrice)
    {
        return toAjax(tShopCoursesPriceService.insertTShopCoursesPrice(tShopCoursesPrice));
    }

    /**
     * 修改课程价格
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCoursesPrice tShopCoursesPrice = tShopCoursesPriceService.selectTShopCoursesPriceById(id);
        mmap.put("tShopCoursesPrice", tShopCoursesPrice);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程价格
     */
    @RequiresPermissions("shop:price:edit")
    @Log(title = "课程价格", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改课程价格信息")
    public AjaxResult editSave(TShopCoursesPrice tShopCoursesPrice)
    {
        return toAjax(tShopCoursesPriceService.updateTShopCoursesPrice(tShopCoursesPrice));
    }

    /**
     * 删除课程价格
     */
    @RequiresPermissions("shop:price:remove")
    @Log(title = "课程价格", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除课程价格信息")
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesPriceService.deleteTShopCoursesPriceByIds(ids));
    }
}
