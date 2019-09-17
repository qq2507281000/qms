package co.tton.qcloud.web.controller.order;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import co.tton.qcloud.system.domain.TOrder;
import co.tton.qcloud.system.service.ITOrderService;

/**
 * 订单信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Api(value="订单管理",tags = "订单管理")
@Controller
@RequestMapping("/order")
public class TOrderController extends BaseController
{
    private String prefix = "order/base";

    @Autowired
    private ITOrderService tOrderService;

    @RequiresPermissions("order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/list";
    }

    /**
     * 查询订单信息列表
     */
    @RequiresPermissions("order:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取订单列表")
    public TableDataInfo list(@ApiParam("订单实体对象") TOrder tOrder)
    {
        startPage();
        List<TOrder> list = tOrderService.selectTOrderList(tOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @RequiresPermissions("order:export")
    @PostMapping("/export")
    @ResponseBody
    @ApiOperation("导出订单信息列表")
    public AjaxResult export(@ApiParam("订单实体对象") TOrder tOrder)
    {
        List<TOrder> list = tOrderService.selectTOrderList(tOrder);
        ExcelUtil<TOrder> util = new ExcelUtil<TOrder>(TOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单信息
     */
    @RequiresPermissions("order:add")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("保存订单信息")
    public AjaxResult addSave(@ApiParam("订单实体对象") TOrder tOrder)
    {
        return toAjax(tOrderService.insertTOrder(tOrder));
    }

    /**
     * 修改订单信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TOrder tOrder = tOrderService.selectTOrderById(id);
        mmap.put("tOrder", tOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单信息
     */
    @RequiresPermissions("order:edit")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("编辑订单信息")
    public AjaxResult editSave(@ApiParam("订单实体对象") TOrder tOrder)
    {
        return toAjax(tOrderService.updateTOrder(tOrder));
    }

    /**
     * 删除订单信息
     */
    @RequiresPermissions("order:remove")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除订单信息")
    public AjaxResult remove(String ids)
    {
        return toAjax(tOrderService.deleteTOrderByIds(ids));
    }
}
