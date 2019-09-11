package co.tton.qcloud.web.controller.conf;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.StringUtils;
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
import co.tton.qcloud.system.domain.TBanner;
import co.tton.qcloud.system.service.ITBannerService;

/**
 * 首页滚动广告Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/conf/banner")
public class TBannerController extends BaseController
{
    /***
     * 前端地址前缀
     */
    private String prefix = "conf/banner";

    @Autowired
    private ITBannerService tBannerService;

    @RequiresPermissions("conf:banner:view")
    @GetMapping()
    public String banner()
    {
        return prefix + "/list";
    }

    /**
     * 查询首页滚动广告列表
     */
    @RequiresPermissions("conf:banner:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TBanner tBanner)
    {
        startPage();
        List<TBanner> list = tBannerService.selectTBannerList(tBanner);
        return getDataTable(list);
    }

    /**
     * 导出首页滚动广告列表
     */
    @RequiresPermissions("conf:banner:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TBanner tBanner)
    {
        List<TBanner> list = tBannerService.selectTBannerList(tBanner);
        ExcelUtil<TBanner> util = new ExcelUtil<TBanner>(TBanner.class);
        return util.exportExcel(list, "banner");
    }

    /**
     * 新增首页滚动广告
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存首页滚动广告
     */
    @RequiresPermissions("conf:banner:add")
    @Log(title = "首页滚动广告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TBanner tBanner)
    {
        //TODO:需要对前端传入的参数进行校验，把NULL值补全。
        tBanner.setId(StringUtils.genericId());



        return toAjax(tBannerService.insertTBanner(tBanner));
    }

    /**
     * 修改首页滚动广告
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TBanner tBanner = tBannerService.selectTBannerById(id);
        mmap.put("tBanner", tBanner);
        return prefix + "/edit";
    }

    /**
     * 修改保存首页滚动广告
     */
    @RequiresPermissions("conf:banner:edit")
    @Log(title = "首页滚动广告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TBanner tBanner)
    {
        return toAjax(tBannerService.updateTBanner(tBanner));
    }

    /**
     * 删除首页滚动广告
     */
    @RequiresPermissions("conf:banner:remove")
    @Log(title = "首页滚动广告", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tBannerService.deleteTBannerByIds(ids));
    }
}
