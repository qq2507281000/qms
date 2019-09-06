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
import co.tton.qcloud.system.domain.TOrderPayment;
import co.tton.qcloud.system.service.ITOrderPaymentService;

/**
 * 订单支付信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/order/payment")
public class TOrderPaymentController extends BaseController
{
    private String prefix = "order/payment";

    @Autowired
    private ITOrderPaymentService tOrderPaymentService;

    @RequiresPermissions("order:payment:view")
    @GetMapping()
    public String payment()
    {
        return prefix + "/list";
    }

    /**
     * 查询订单支付信息列表
     */
    @RequiresPermissions("order:payment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TOrderPayment tOrderPayment)
    {
        startPage();
        List<TOrderPayment> list = tOrderPaymentService.selectTOrderPaymentList(tOrderPayment);
        return getDataTable(list);
    }

    /**
     * 导出订单支付信息列表
     */
    @RequiresPermissions("order:payment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TOrderPayment tOrderPayment)
    {
        List<TOrderPayment> list = tOrderPaymentService.selectTOrderPaymentList(tOrderPayment);
        ExcelUtil<TOrderPayment> util = new ExcelUtil<TOrderPayment>(TOrderPayment.class);
        return util.exportExcel(list, "order-payment");
    }

    /**
     * 新增订单支付信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单支付信息
     */
    @RequiresPermissions("order:payment:add")
    @Log(title = "订单支付信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TOrderPayment tOrderPayment)
    {
        return toAjax(tOrderPaymentService.insertTOrderPayment(tOrderPayment));
    }

    /**
     * 修改订单支付信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TOrderPayment tOrderPayment = tOrderPaymentService.selectTOrderPaymentById(id);
        mmap.put("tOrderPayment", tOrderPayment);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单支付信息
     */
    @RequiresPermissions("order:payment:edit")
    @Log(title = "订单支付信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TOrderPayment tOrderPayment)
    {
        return toAjax(tOrderPaymentService.updateTOrderPayment(tOrderPayment));
    }

    /**
     * 删除订单支付信息
     */
    @RequiresPermissions("order:payment:remove")
    @Log(title = "订单支付信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tOrderPaymentService.deleteTOrderPaymentByIds(ids));
    }
}
