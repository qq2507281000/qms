package co.tton.qcloud.web.controller.system;

import java.util.List;

import co.tton.qcloud.common.annotation.AllowAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysMenu;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.service.ISysMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页 业务处理
 * 
 * @author Qcloud
 */
@Controller
@RequestMapping("/admin")
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    // 系统首页
    @GetMapping("/index")
    @AllowAdmin()
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        mmap.put("demoEnabled", Global.isDemoEnabled());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    @AllowAdmin()
    public String main(ModelMap mmap)
    {
        mmap.put("version", Global.getVersion());
        return "main_v1";
    }
}
