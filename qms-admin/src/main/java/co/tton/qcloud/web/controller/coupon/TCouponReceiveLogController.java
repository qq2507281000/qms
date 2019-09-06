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
import co.tton.qcloud.system.domain.TCouponReceiveLog;
import co.tton.qcloud.system.service.ITCouponReceiveLogService;

/**
 * 优惠券领用记录Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/coupon/receive-log")
public class TCouponReceiveLogController extends BaseController
{
    private String prefix = "coupon/log";

    @Autowired
    private ITCouponReceiveLogService tCouponReceiveLogService;

    @RequiresPermissions("coupon:received-log:view")
    @GetMapping()
    public String log()
    {
        return prefix + "/log";
    }

    /**
     * 查询优惠券领用记录列表
     */
    @RequiresPermissions("coupon:received-log:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TCouponReceiveLog tCouponReceiveLog)
    {
        startPage();
        List<TCouponReceiveLog> list = tCouponReceiveLogService.selectTCouponReceiveLogList(tCouponReceiveLog);
        return getDataTable(list);
    }

    /**
     * 导出优惠券领用记录列表
     */
    @RequiresPermissions("coupon:received-log:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TCouponReceiveLog tCouponReceiveLog)
    {
        List<TCouponReceiveLog> list = tCouponReceiveLogService.selectTCouponReceiveLogList(tCouponReceiveLog);
        ExcelUtil<TCouponReceiveLog> util = new ExcelUtil<TCouponReceiveLog>(TCouponReceiveLog.class);
        return util.exportExcel(list, "log");
    }

    /**
     * 新增优惠券领用记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存优惠券领用记录
     */
    @RequiresPermissions("coupon:received-log:add")
    @Log(title = "优惠券领用记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TCouponReceiveLog tCouponReceiveLog)
    {
        return toAjax(tCouponReceiveLogService.insertTCouponReceiveLog(tCouponReceiveLog));
    }

    /**
     * 修改优惠券领用记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TCouponReceiveLog tCouponReceiveLog = tCouponReceiveLogService.selectTCouponReceiveLogById(id);
        mmap.put("tCouponReceiveLog", tCouponReceiveLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存优惠券领用记录
     */
    @RequiresPermissions("coupon:received-log:edit")
    @Log(title = "优惠券领用记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TCouponReceiveLog tCouponReceiveLog)
    {
        return toAjax(tCouponReceiveLogService.updateTCouponReceiveLog(tCouponReceiveLog));
    }

    /**
     * 删除优惠券领用记录
     */
    @RequiresPermissions("coupon:received-log:remove")
    @Log(title = "优惠券领用记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tCouponReceiveLogService.deleteTCouponReceiveLogByIds(ids));
    }
}
