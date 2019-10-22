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
    @ApiOperation("首页推荐商家查询，查询所有商家，根据分类查商家")
//    @RequiresPermissions("wx:shop:suggest")
    @RequestMapping(value="/suggest",method = RequestMethod.GET)
    public AjaxResult<List<TShop>> getSuggestShop(@RequestParam(value="loc",required = false) String location,
                                     @RequestParam(value="category",required = false)String categoryId,
                                     @RequestParam(value="suggest",required = false)Integer suggest)
    {
          TShop tShop = new TShop();
          tShop.setAddress(location);
          tShop.setCategoryId(categoryId);
          tShop.setSuggest(suggest);
          List<TShop> listTShop=tShopService.getSuggestShop(tShop);
          if(StringUtils.isNotNull(listTShop)){
            return AjaxResult.success("获取商家信息成功。",listTShop);
          }else{
            return AjaxResult.success("获取商家信息失败。",listTShop);
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
    public AjaxResult<List<TShop>> getShopDetail(@PathVariable("id")String shopId,
                                                 @RequestParam(value="loc",required = false) String location){
        if(StringUtils.isNotEmpty(shopId)){
          TShop tShop = new TShop();
          tShop.setId(shopId);
          tShop.setAddress(location);
          List list=tShopService.getShopDetail(tShop);
          if(StringUtils.isNotEmpty(list)){
            return AjaxResult.success("获取商家详情成功。",list);
          }else{
            return AjaxResult.success("获取商家详情失败。",list);
          }
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
  @RequestMapping(value="/name",method = RequestMethod.GET)
  public AjaxResult<List> getNameShop(@RequestParam(value="name")String name)
  {
      if(StringUtils.isNotEmpty(name)){
        //查询商家表
        List listTShop=tShopService.getNameShop(name);
        List list = new ArrayList();
        if(StringUtils.isNotEmpty(listTShop)){
          list.add(listTShop);
        }
        //查询课程表
        List listTShopCourses=tShopCoursesService.getNameShopCourses(name);
        if(StringUtils.isNotEmpty(listTShopCourses)){
          list.add(listTShopCourses);
        }
        if(StringUtils.isNotEmpty(list)){
          return AjaxResult.success("获取信息成功。",list);
        }else{
          return AjaxResult.success("无数据。",list);
        }
      }else{
        return AjaxResult.error("无数据。");
      }
  }

}
