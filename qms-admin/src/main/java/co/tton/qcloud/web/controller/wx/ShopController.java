package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:30
 */
@RestController
@RequestMapping("/api/v1.0/shop")
public class ShopController extends BaseController {

    @RequiresPermissions("wx:shop:suggest")
    @RequestMapping(value="/suggest",method = RequestMethod.GET)
    public AjaxResult getSuggestShop(@RequestParam(value="loc",required = false) String location,
                                     @RequestParam(value="category",required = false)String categoryId){
        return null;
    }

    @RequiresPermissions("wx:shop:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    public AjaxResult getShopList(@RequestParam(value="loc",required = false) String location,
                                  @RequestParam(value="category",required = false)String categoryId){
        return null;
    }

    @RequiresPermissions("wx:shop:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public AjaxResult getShopDetail(@PathVariable("id")String shopId){
        return null;
    }

}
