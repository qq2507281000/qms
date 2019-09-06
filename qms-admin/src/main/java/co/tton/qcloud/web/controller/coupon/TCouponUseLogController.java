package co.tton.qcloud.web.controller.coupon;

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
import co.tton.qcloud.system.domain.TCouponUseLog;
import co.tton.qcloud.system.service.ITCouponUseLogService;

/**
 * 优惠券使用日志Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/coupon/use-log")
public class TCouponUseLogController extends BaseController
{
    private String prefix = "system/log";

    @Autowired
    private ITCouponUseLogService tCouponUseLogService;

    @RequiresPermissions("coupon:use-log:view")
    @GetMapping()
    public String log()
    {
        return prefix + "/log";
    }

    /**
     * 查询优惠券使用日志列表
     */
    @RequiresPermissions("coupon:use-log:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TCouponUseLog tCouponUseLog)
    {
        startPage();
        List<TCouponUseLog> list = tCouponUseLogService.selectTCouponUseLogList(tCouponUseLog);
        return getDataTable(list);
    }

    /**
     * 导出优惠券使用日志列表
     */
    @RequiresPermissions("coupon:use-log:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TCouponUseLog tCouponUseLog)
    {
        List<TCouponUseLog> list = tCouponUseLogService.selectTCouponUseLogList(tCouponUseLog);
        ExcelUtil<TCouponUseLog> util = new ExcelUtil<TCouponUseLog>(TCouponUseLog.class);
        return util.exportExcel(list, "log");
    }

    /**
     * 新增优惠券使用日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存优惠券使用日志
     */
    @RequiresPermissions("coupon:use-log:add")
    @Log(title = "优惠券使用日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TCouponUseLog tCouponUseLog)
    {
        return toAjax(tCouponUseLogService.insertTCouponUseLog(tCouponUseLog));
    }

    /**
     * 修改优惠券使用日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TCouponUseLog tCouponUseLog = tCouponUseLogService.selectTCouponUseLogById(id);
        mmap.put("tCouponUseLog", tCouponUseLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存优惠券使用日志
     */
    @RequiresPermissions("coupon:use-log:edit")
    @Log(title = "优惠券使用日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TCouponUseLog tCouponUseLog)
    {
        return toAjax(tCouponUseLogService.updateTCouponUseLog(tCouponUseLog));
    }

    /**
     * 删除优惠券使用日志
     */
    @RequiresPermissions("coupon:use-log:remove")
    @Log(title = "优惠券使用日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tCouponUseLogService.deleteTCouponUseLogByIds(ids));
    }
}
