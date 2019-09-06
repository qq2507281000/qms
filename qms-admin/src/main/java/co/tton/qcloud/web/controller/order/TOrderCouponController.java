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
import co.tton.qcloud.system.domain.TOrderCoupon;
import co.tton.qcloud.system.service.ITOrderCouponService;

/**
 * 订单优惠券信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/order/coupon")
public class TOrderCouponController extends BaseController
{
    private String prefix = "order/coupon";

    @Autowired
    private ITOrderCouponService tOrderCouponService;

    @RequiresPermissions("order:coupon:view")
    @GetMapping()
    public String coupon()
    {
        return prefix + "/list";
    }

    /**
     * 查询订单优惠券信息列表
     */
    @RequiresPermissions("order:coupon:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TOrderCoupon tOrderCoupon)
    {
        startPage();
        List<TOrderCoupon> list = tOrderCouponService.selectTOrderCouponList(tOrderCoupon);
        return getDataTable(list);
    }

    /**
     * 导出订单优惠券信息列表
     */
    @RequiresPermissions("order:coupon:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TOrderCoupon tOrderCoupon)
    {
        List<TOrderCoupon> list = tOrderCouponService.selectTOrderCouponList(tOrderCoupon);
        ExcelUtil<TOrderCoupon> util = new ExcelUtil<TOrderCoupon>(TOrderCoupon.class);
        return util.exportExcel(list, "order-coupon");
    }

    /**
     * 新增订单优惠券信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单优惠券信息
     */
    @RequiresPermissions("order:coupon:add")
    @Log(title = "订单优惠券信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TOrderCoupon tOrderCoupon)
    {
        return toAjax(tOrderCouponService.insertTOrderCoupon(tOrderCoupon));
    }

    /**
     * 修改订单优惠券信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TOrderCoupon tOrderCoupon = tOrderCouponService.selectTOrderCouponById(id);
        mmap.put("tOrderCoupon", tOrderCoupon);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单优惠券信息
     */
    @RequiresPermissions("order:coupon:edit")
    @Log(title = "订单优惠券信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TOrderCoupon tOrderCoupon)
    {
        return toAjax(tOrderCouponService.updateTOrderCoupon(tOrderCoupon));
    }

    /**
     * 删除订单优惠券信息
     */
    @RequiresPermissions("order:coupon:remove")
    @Log(title = "订单优惠券信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tOrderCouponService.deleteTOrderCouponByIds(ids));
    }
}
