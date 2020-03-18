package co.tton.qcloud.web.controller.homePage;

import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.domain.HomePageModel;
import co.tton.qcloud.system.service.IHomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页信息Controller
 *
 * @author qcloud
 * @date 2020-03-16
 */
@Api(value = "首页信息",tags="首页信息")
@Controller
@RequestMapping("/homePage")
public class homePageController extends BaseController {

    @Autowired
    private IHomePageService iHomePageService;


    /**
     * 查询首页信息
     */
    @ApiOperation("查询首页信息")
    @GetMapping("")
    @ResponseBody
    public HomePageModel getHomePage()
    {
        return iHomePageService.getHomePage();
    }
}
