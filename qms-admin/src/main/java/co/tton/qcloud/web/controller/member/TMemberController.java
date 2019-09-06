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
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.service.ITMemberService;

/**
 * 会员基本信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/member")
public class TMemberController extends BaseController
{
    private String prefix = "member/base";

    @Autowired
    private ITMemberService tMemberService;

    @RequiresPermissions("member:view")
    @GetMapping()
    public String member()
    {
        return prefix + "/list";
    }

    /**
     * 查询会员基本信息列表
     */
    @RequiresPermissions("member:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TMember tMember)
    {
        startPage();
        List<TMember> list = tMemberService.selectTMemberList(tMember);
        return getDataTable(list);
    }

    /**
     * 导出会员基本信息列表
     */
    @RequiresPermissions("member:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TMember tMember)
    {
        List<TMember> list = tMemberService.selectTMemberList(tMember);
        ExcelUtil<TMember> util = new ExcelUtil<TMember>(TMember.class);
        return util.exportExcel(list, "member");
    }

    /**
     * 新增会员基本信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存会员基本信息
     */
    @RequiresPermissions("member:add")
    @Log(title = "会员基本信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TMember tMember)
    {
        return toAjax(tMemberService.insertTMember(tMember));
    }

    /**
     * 修改会员基本信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TMember tMember = tMemberService.selectTMemberById(id);
        mmap.put("tMember", tMember);
        return prefix + "/edit";
    }

    /**
     * 修改保存会员基本信息
     */
    @RequiresPermissions("member:edit")
    @Log(title = "会员基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TMember tMember)
    {
        return toAjax(tMemberService.updateTMember(tMember));
    }

    /**
     * 删除会员基本信息
     */
    @RequiresPermissions("member:remove")
    @Log(title = "会员基本信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tMemberService.deleteTMemberByIds(ids));
    }
}
