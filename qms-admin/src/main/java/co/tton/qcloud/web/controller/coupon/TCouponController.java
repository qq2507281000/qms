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
import co.tton.qcloud.system.domain.TCoupon;
import co.tton.qcloud.system.service.ITCouponService;

/**
 * 平台优惠券Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/coupon")
public class TCouponController extends BaseController
{
    private String prefix = "/coupon/base";

    @Autowired
    private ITCouponService tCouponService;

    @RequiresPermissions("coupon:view")
    @GetMapping()
    public String coupon()
    {
        return prefix + "/list";
    }

    /**
     * 查询平台优惠券列表
     */
    @RequiresPermissions("coupon:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TCoupon tCoupon)
    {
        startPage();
        List<TCoupon> list = tCouponService.selectTCouponList(tCoupon);
        return getDataTable(list);
    }

    /**
     * 导出平台优惠券列表
     */
    @RequiresPermissions("coupon:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TCoupon tCoupon)
    {
        List<TCoupon> list = tCouponService.selectTCouponList(tCoupon);
        ExcelUtil<TCoupon> util = new ExcelUtil<TCoupon>(TCoupon.class);
        return util.exportExcel(list, "coupon");
    }

    /**
     * 新增平台优惠券
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存平台优惠券
     */
    @RequiresPermissions("coupon:add")
    @Log(title = "平台优惠券", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TCoupon tCoupon)
    {
        return toAjax(tCouponService.insertTCoupon(tCoupon));
    }

    /**
     * 修改平台优惠券
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TCoupon tCoupon = tCouponService.selectTCouponById(id);
        mmap.put("tCoupon", tCoupon);
        return prefix + "/edit";
    }

    /**
     * 修改保存平台优惠券
     */
    @RequiresPermissions("coupon:edit")
    @Log(title = "平台优惠券", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TCoupon tCoupon)
    {
        return toAjax(tCouponService.updateTCoupon(tCoupon));
    }

    /**
     * 删除平台优惠券
     */
    @RequiresPermissions("coupon:remove")
    @Log(title = "平台优惠券", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tCouponService.deleteTCouponByIds(ids));
    }
}
