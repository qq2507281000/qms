package co.tton.qcloud.web.controller.order;

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
import co.tton.qcloud.system.domain.TOrderUseLog;
import co.tton.qcloud.system.service.ITOrderUseLogService;

/**
 * 订单使用状况Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/order/use-log")
public class TOrderUseLogController extends BaseController
{
    private String prefix = "order/log";

    @Autowired
    private ITOrderUseLogService tOrderUseLogService;

    @RequiresPermissions("order:log:view")
    @GetMapping()
    public String log()
    {
        return prefix + "/list";
    }

    /**
     * 查询订单使用状况列表
     */
    @RequiresPermissions("order:log:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TOrderUseLog tOrderUseLog)
    {
        startPage();
        List<TOrderUseLog> list = tOrderUseLogService.selectTOrderUseLogList(tOrderUseLog);
        return getDataTable(list);
    }

    /**
     * 导出订单使用状况列表
     */
    @RequiresPermissions("order:log:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TOrderUseLog tOrderUseLog)
    {
        List<TOrderUseLog> list = tOrderUseLogService.selectTOrderUseLogList(tOrderUseLog);
        ExcelUtil<TOrderUseLog> util = new ExcelUtil<TOrderUseLog>(TOrderUseLog.class);
        return util.exportExcel(list, "order-log");
    }

    /**
     * 新增订单使用状况
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单使用状况
     */
    @RequiresPermissions("order:log:add")
    @Log(title = "订单使用状况", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TOrderUseLog tOrderUseLog)
    {
        return toAjax(tOrderUseLogService.insertTOrderUseLog(tOrderUseLog));
    }

    /**
     * 修改订单使用状况
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TOrderUseLog tOrderUseLog = tOrderUseLogService.selectTOrderUseLogById(id);
        mmap.put("tOrderUseLog", tOrderUseLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单使用状况
     */
    @RequiresPermissions("order:log:edit")
    @Log(title = "订单使用状况", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TOrderUseLog tOrderUseLog)
    {
        return toAjax(tOrderUseLogService.updateTOrderUseLog(tOrderUseLog));
    }

    /**
     * 删除订单使用状况
     */
    @RequiresPermissions("order:log:remove")
    @Log(title = "订单使用状况", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tOrderUseLogService.deleteTOrderUseLogByIds(ids));
    }
}
