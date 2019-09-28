package co.tton.qcloud.web.controller.conf;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
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
import co.tton.qcloud.system.domain.TTopic;
import co.tton.qcloud.system.service.ITTopicService;

/**
 * 专题信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/conf/topic")
public class TTopicController extends BaseController
{
    /***
     * 前端地址
     */
    private String prefix = "conf/topic";

    @Autowired
    private ITTopicService tTopicService;

    @RequiresPermissions("conf:topic:view")
    @GetMapping()
    public String topic()
    {
        return prefix + "/list";
    }

    /**
     * 查询专题信息列表
     */
    @RequiresPermissions("conf:topic:list")
    @PostMapping("/list")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public TableDataInfo list(TTopic tTopic)
    {
        startPage();
        List<TTopic> list = tTopicService.selectTTopicList(tTopic);
        return getDataTable(list);
    }

    /**
     * 导出专题信息列表
     */
    @RequiresPermissions("conf:topic:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TTopic tTopic)
    {
        List<TTopic> list = tTopicService.selectTTopicList(tTopic);
        ExcelUtil<TTopic> util = new ExcelUtil<TTopic>(TTopic.class);
        return util.exportExcel(list, "topic");
    }

    /**
     * 新增专题信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存专题信息
     */
    @RequiresPermissions("conf:topic:add")
    @Log(title = "专题信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public AjaxResult addSave(TTopic tTopic)
    {
        return toAjax(tTopicService.insertTTopic(tTopic));
    }

    /**
     * 修改专题信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TTopic tTopic = tTopicService.selectTTopicById(id);
        mmap.put("tTopic", tTopic);
        return prefix + "/edit";
    }

    /**
     * 修改保存专题信息
     */
    @RequiresPermissions("conf:topic:edit")
    @Log(title = "专题信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public AjaxResult editSave(TTopic tTopic)
    {
        return toAjax(tTopicService.updateTTopic(tTopic));
    }

    /**
     * 删除专题信息
     */
    @RequiresPermissions("conf:topic:remove")
    @Log(title = "专题信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tTopicService.deleteTTopicByIds(ids));
    }
}
