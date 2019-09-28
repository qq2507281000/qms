package co.tton.qcloud.web.controller.shop;

import java.util.List;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.ServletUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.service.ITShopService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商家信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Api(value = "商家信息",tags="商家信息")
@Controller
@RequestMapping("/shop")
public class TShopController extends BaseController
{
    private String prefix = "shop/base";

    @Autowired
    private ITShopService tShopService;

    @RequiresPermissions("shop:view")
    @GetMapping()
    public String shop()
    {
        return prefix + "/shop";
    }

    /**
     * 查询商家信息列表
     */
    @ApiOperation("获取商家列表")
    @RequiresPermissions("shop:list")
    @PostMapping("/list")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public TableDataInfo list(TShop tShop)
    {
        startPage();
        List<TShop> list = tShopService.selectTShopList(tShop);
        return getDataTable(list);
    }

    /**
     * 新增商家信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存商家信息
     */
    @ApiOperation("新增商家信息")
    @RequiresPermissions("shop:add")
    @Log(title = "商家信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public AjaxResult addSave(TShop tShop)
    {
        return toAjax(tShopService.insertTShop(tShop));
    }

    /**
     * 修改商家信息
     */
    @ApiOperation("获取商家详细")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        TShop tShop = tShopService.selectTShopById(id);
        mmap.put("tShop", tShop);
        return prefix + "/edit";
    }

    /**
     * 修改保存商家信息
     */
    @ApiOperation("更新商家信息")
    @RequiresPermissions("shop:edit")
    @Log(title = "商家信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult editSave(TShop tShop)
    {
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            if(user.getShopId() == tShop.getId()){
                return toAjax(tShopService.updateTShop(tShop));
            }
            else{
                return error("不能修改其他商家信息。");
            }
        }
        return toAjax(tShopService.updateTShop(tShop));
    }

    /**
     * 删除商家信息
     */
    @ApiOperation("删除商家信息")
    @RequiresPermissions("shop:remove")
    @Log(title = "商家信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @RoleScope(roleDefined={"ADMIN"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopService.deleteTShopByIds(ids));
    }
}
