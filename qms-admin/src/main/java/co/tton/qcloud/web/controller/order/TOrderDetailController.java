package co.tton.qcloud.web.controller.order;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.system.domain.TOrderDetailModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import co.tton.qcloud.system.domain.TOrderDetail;
import co.tton.qcloud.system.service.ITOrderDetailService;

/**
 * 订单详情Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/order/detail")
public class TOrderDetailController extends BaseController
{
    private String prefix = "order/detail";

    @Autowired
    private ITOrderDetailService tOrderDetailService;

    @RequiresPermissions("order:detail:view")
    @GetMapping()
    public String detail()
    {
        return prefix + "/list";
    }

    /**
     * 查询订单详情列表
     */
    @RequiresPermissions("order:detail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TOrderDetail tOrderDetail)
    {
        startPage();
        List<TOrderDetail> list = tOrderDetailService.selectTOrderDetailList(tOrderDetail);
        return getDataTable(list);
    }

    /***
     * 查询订单详细列表
     * @param orderId
     * @return
     */
    @RequiresPermissions("order:detail")
    @PostMapping("/{order-id}")
    @ResponseBody
    public TableDataInfo details(@PathVariable("order-id") String orderId){
        startPage();
        List<TOrderDetailModel> list = tOrderDetailService.selectTOrderDetailModelList(orderId);
        return getDataTable(list);
    }

    /**
     * 导出订单详情列表
     */
    @RequiresPermissions("order:detail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TOrderDetail tOrderDetail)
    {
        List<TOrderDetail> list = tOrderDetailService.selectTOrderDetailList(tOrderDetail);
        ExcelUtil<TOrderDetail> util = new ExcelUtil<TOrderDetail>(TOrderDetail.class);
        return util.exportExcel(list, "order-detail");
    }

    /**
     * 新增订单详情
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单详情
     */
    @RequiresPermissions("order:detail:add")
    @Log(title = "订单详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TOrderDetail tOrderDetail)
    {
        return toAjax(tOrderDetailService.insertTOrderDetail(tOrderDetail));
    }

    /**
     * 修改订单详情
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TOrderDetail tOrderDetail = tOrderDetailService.selectTOrderDetailById(id);
        mmap.put("tOrderDetail", tOrderDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单详情
     */
    @RequiresPermissions("order:detail:edit")
    @Log(title = "订单详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TOrderDetail tOrderDetail)
    {
        return toAjax(tOrderDetailService.updateTOrderDetail(tOrderDetail));
    }

    /**
     * 删除订单详情
     */
    @RequiresPermissions("order:detail:remove")
    @Log(title = "订单详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tOrderDetailService.deleteTOrderDetailByIds(ids));
    }
}
