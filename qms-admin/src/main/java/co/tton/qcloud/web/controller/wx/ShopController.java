package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TShop;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.system.wxservice.ITShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-23
 */
@Api(value = "小程序商家信息",tags="小程序商家信息")
@RestController
@RequestMapping("/api/v1.0/shop")
public class ShopController extends BaseController {

    @Autowired
    private ITShopService tShopService;
    @Autowired
    private ITShopCoursesService tShopCoursesService;

  /***
   *
   * @param location,categoryId,suggest
   * @return
   */
    @ApiOperation("首页推荐商家查询，查询所有商家")
//    @RequiresPermissions("wx:shop:suggest")
    @RequestMapping(value="/suggest",method = RequestMethod.GET)
    public AjaxResult<List<TShop>> getSuggestShop(@RequestParam(value="loc",required = false) String location,
                                     @RequestParam(value="category",required = false)String categoryId,
                                     @RequestParam(value="suggest",required = false)Integer suggest)
    {
        if(StringUtils.isNotEmpty(location) && location.equals("大连")){
                List<TShop> listTShop=tShopService.getSuggestShop(categoryId,suggest);
                return AjaxResult.success("获取商家信息成功。",listTShop);
        }else{
            return AjaxResult.error("地点错误。");
        }
    }

  /***
   *
   * @param shopId
   * @return
   */
    @ApiOperation("查询商家详情")
//    @RequiresPermissions("wx:shop:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public AjaxResult<List<TShop>> getShopDetail(@PathVariable("id")String shopId){
        if(StringUtils.isNotEmpty(shopId)){
          List list=tShopService.getShopDetail(shopId);
          return AjaxResult.success("获取商家详情成功。",list);
        }else{
          return AjaxResult.error("商家ID错误。");
        }
    }

  /***
   *
   * @param name
   * @return
   */
  @ApiOperation("搜索框查询")
//  @RequiresPermissions("wx:shop:suggest")
  @RequestMapping(value="/getName",method = RequestMethod.GET)
  public AjaxResult<List> getNameShop(@RequestParam(value="name")String name)
  {
    if(StringUtils.isNotEmpty(name)){
      //查询商家表
      List listTShop=tShopService.getNameShop(name);
      List list = new ArrayList();
      list.add(listTShop);
      //查询课程表
      List listTShopCourses=tShopCoursesService.getNameShopCourses(name);
      list.add(listTShopCourses);
      return AjaxResult.success("获取信息成功。",list);
    }else{
      return AjaxResult.error("名称错误");
    }
  }

}
