package co.tton.qcloud.web.controller.system;

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
import co.tton.qcloud.system.domain.TCity;
import co.tton.qcloud.system.service.ITCityService;

/**
 * cityController
 * 
 * @author qcloud
 * @date 2019-10-17
 */
@Controller
@RequestMapping("/system/city")
public class TCityController extends BaseController
{
    private String prefix = "system/city";

    @Autowired
    private ITCityService tCityService;

    @RequiresPermissions("system:city:view")
    @GetMapping()
    public String city()
    {
        return prefix + "/city";
    }

    /**
     * 查询city列表
     */
    @RequiresPermissions("system:city:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TCity tCity)
    {
        startPage();
        List<TCity> list = tCityService.selectTCityList(tCity);
        return getDataTable(list);
    }

    /**
     * 导出city列表
     */
    @RequiresPermissions("system:city:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TCity tCity)
    {
        List<TCity> list = tCityService.selectTCityList(tCity);
        ExcelUtil<TCity> util = new ExcelUtil<TCity>(TCity.class);
        return util.exportExcel(list, "city");
    }

    /**
     * 新增city
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存city
     */
    @RequiresPermissions("system:city:add")
    @Log(title = "city", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TCity tCity)
    {
        tCity.setId(StringUtils.genericId());
        return toAjax(tCityService.insertTCity(tCity));
    }

    /**
     * 修改city
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TCity tCity = tCityService.selectTCityById(id);
        mmap.put("tCity", tCity);
        return prefix + "/edit";
    }

    /**
     * 修改保存city
     */
    @RequiresPermissions("system:city:edit")
    @Log(title = "city", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TCity tCity)
    {
        return toAjax(tCityService.updateTCity(tCity));
    }

    /**
     * 删除city
     */
    @RequiresPermissions("system:city:remove")
    @Log(title = "city", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tCityService.deleteTCityByIds(ids));
    }
}
