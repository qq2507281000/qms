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
import co.tton.qcloud.system.domain.TCouponGrant;
import co.tton.qcloud.system.service.ITCouponGrantService;
/**
 * 优惠券生成参数库Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/coupon/grant")
public class TCouponGrantController extends BaseController
{
    private String prefix = "coupon/grant";

    @Autowired
    private ITCouponGrantService tCouponGrantService;

    @RequiresPermissions("coupon:grant:view")
    @GetMapping()
    public String grant()
    {
        return prefix + "/list";
    }

    /**
     * 查询优惠券生成参数库列表
     */
    @RequiresPermissions("coupon:grant:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TCouponGrant tCouponGrant)
    {
        startPage();
        List<TCouponGrant> list = tCouponGrantService.selectTCouponGrantList(tCouponGrant);
        return getDataTable(list);
    }

    /**
     * 导出优惠券生成参数库列表
     */
    @RequiresPermissions("coupon:grant:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TCouponGrant tCouponGrant)
    {
        List<TCouponGrant> list = tCouponGrantService.selectTCouponGrantList(tCouponGrant);
        ExcelUtil<TCouponGrant> util = new ExcelUtil<TCouponGrant>(TCouponGrant.class);
        return util.exportExcel(list, "coupon-grant");
    }

    /**
     * 新增优惠券生成参数库
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存优惠券生成参数库
     */
    @RequiresPermissions("coupon:grant:add")
    @Log(title = "优惠券生成参数库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TCouponGrant tCouponGrant)
    {
        return toAjax(tCouponGrantService.insertTCouponGrant(tCouponGrant));
    }

    /**
     * 修改优惠券生成参数库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TCouponGrant tCouponGrant = tCouponGrantService.selectTCouponGrantById(id);
        mmap.put("tCouponGrant", tCouponGrant);
        return prefix + "/edit";
    }

    /**
     * 修改保存优惠券生成参数库
     */
    @RequiresPermissions("coupon:grant:edit")
    @Log(title = "优惠券生成参数库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TCouponGrant tCouponGrant)
    {
        return toAjax(tCouponGrantService.updateTCouponGrant(tCouponGrant));
    }

    /**
     * 删除优惠券生成参数库
     */
    @RequiresPermissions("coupon:grant:remove")
    @Log(title = "优惠券生成参数库", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tCouponGrantService.deleteTCouponGrantByIds(ids));
    }
}
