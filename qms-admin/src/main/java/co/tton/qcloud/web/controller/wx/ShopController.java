package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.wxservice.ITShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-23
 */
@Api(value = "小程序商家信息",tags="小程序微信商家信息")
@RestController
@RequestMapping("/api/v1.0/shop")
public class ShopController extends BaseController {

    @Autowired
    private ITShopService tShopService;

    @ApiOperation("首页推荐商家查询，查询所有商家，搜索框查询")
    @RequiresPermissions("wx:shop:suggest")
    @RequestMapping(value="/suggest",method = RequestMethod.GET)
    public AjaxResult getSuggestShop(@RequestParam(value="loc",required = false) String location,
                                     @RequestParam(value="category",required = false)String categoryId,
                                     @RequestParam(value="suggest",required = false)Integer suggest,
                                     @RequestParam(value="name",required = false)String name)
    {
        if(location.equals("dalian")){
                List list=tShopService.getSuggestShop(categoryId,suggest,name);
                return AjaxResult.success("获取商家信息成功。",list);
        }else{
            return AjaxResult.success("location错误。");
        }
    }

    @ApiOperation("查询商家详情")
    @RequiresPermissions("wx:shop:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public AjaxResult getShopDetail(@PathVariable("id")String shopId){
      if(StringUtils.isNotEmpty(shopId)){
        TShop tShop=tShopService.getShopDetail(shopId);
        return AjaxResult.success("获取商家详情成功。",tShop);
      }else{
        return AjaxResult.success("商家ID错误。");
      }
    }

}
