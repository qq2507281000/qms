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
import co.tton.qcloud.system.domain.TShopCoursesTime;
import co.tton.qcloud.system.service.ITShopCoursesTimeService;

/**
 * 课程时间Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/courses/time")
public class TShopCoursesTimeController extends BaseController
{
    private String prefix = "shop/time";

    @Autowired
    private ITShopCoursesTimeService tShopCoursesTimeService;

    @RequiresPermissions("shop:courses:time:view")
    @GetMapping()
    public String time()
    {
        return prefix + "/time";
    }

    /**
     * 查询课程时间列表
     */
    @RequiresPermissions("shop:courses:time:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TShopCoursesTime tShopCoursesTime)
    {
        startPage();
        List<TShopCoursesTime> list = tShopCoursesTimeService.selectTShopCoursesTimeList(tShopCoursesTime);
        return getDataTable(list);
    }

    /**
     * 导出课程时间列表
     */
    @RequiresPermissions("shop:courses:time:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TShopCoursesTime tShopCoursesTime)
    {
        List<TShopCoursesTime> list = tShopCoursesTimeService.selectTShopCoursesTimeList(tShopCoursesTime);
        ExcelUtil<TShopCoursesTime> util = new ExcelUtil<TShopCoursesTime>(TShopCoursesTime.class);
        return util.exportExcel(list, "time");
    }

    /**
     * 新增课程时间
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存课程时间
     */
    @RequiresPermissions("shop:courses:time:add")
    @Log(title = "课程价格", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TShopCoursesTime tShopCoursesTime)
    {
        return toAjax(tShopCoursesTimeService.insertTShopCoursesTime(tShopCoursesTime));
    }

    /**
     * 修改课程时间
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TShopCoursesTime tShopCoursesTime = tShopCoursesTimeService.selectTShopCoursesTimeById(id);
        mmap.put("tShopCoursesTime", tShopCoursesTime);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程时间
     */
    @RequiresPermissions("shop:courses:time:edit")
    @Log(title = "课程价格", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TShopCoursesTime tShopCoursesTime)
    {
        return toAjax(tShopCoursesTimeService.updateTShopCoursesTime(tShopCoursesTime));
    }

    /**
     * 删除课程时间
     */
    @RequiresPermissions("shop:courses:time:remove")
    @Log(title = "课程价格", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopCoursesTimeService.deleteTShopCoursesTimeByIds(ids));
    }
}
