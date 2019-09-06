package co.tton.qcloud.web.controller.member;

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
import co.tton.qcloud.system.domain.TMemberBaby;
import co.tton.qcloud.system.service.ITMemberBabyService;

/**
 * 会员孩子信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/member/child")
public class TMemberBabyController extends BaseController
{
    private String prefix = "member/baby";

    @Autowired
    private ITMemberBabyService tMemberBabyService;

    @RequiresPermissions("member:child:view")
    @GetMapping()
    public String baby()
    {
        return prefix + "/list";
    }

    /**
     * 查询会员孩子信息列表
     */
    @RequiresPermissions("member:child:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TMemberBaby tMemberBaby)
    {
        startPage();
        List<TMemberBaby> list = tMemberBabyService.selectTMemberBabyList(tMemberBaby);
        return getDataTable(list);
    }

    /**
     * 导出会员孩子信息列表
     */
    @RequiresPermissions("member:child:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TMemberBaby tMemberBaby)
    {
        List<TMemberBaby> list = tMemberBabyService.selectTMemberBabyList(tMemberBaby);
        ExcelUtil<TMemberBaby> util = new ExcelUtil<TMemberBaby>(TMemberBaby.class);
        return util.exportExcel(list, "baby");
    }

    /**
     * 新增会员孩子信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存会员孩子信息
     */
    @RequiresPermissions("member:child:add")
    @Log(title = "会员孩子信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TMemberBaby tMemberBaby)
    {
        return toAjax(tMemberBabyService.insertTMemberBaby(tMemberBaby));
    }

    /**
     * 修改会员孩子信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TMemberBaby tMemberBaby = tMemberBabyService.selectTMemberBabyById(id);
        mmap.put("tMemberBaby", tMemberBaby);
        return prefix + "/edit";
    }

    /**
     * 修改保存会员孩子信息
     */
    @RequiresPermissions("member:child:edit")
    @Log(title = "会员孩子信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TMemberBaby tMemberBaby)
    {
        return toAjax(tMemberBabyService.updateTMemberBaby(tMemberBaby));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("member:child:remove")
    @Log(title = "会员孩子信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tMemberBabyService.deleteTMemberBabyByIds(ids));
    }
}
