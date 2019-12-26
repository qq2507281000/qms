package co.tton.qcloud.web.controller.recommend;

import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.annotation.RoleScope;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.common.utils.poi.ExcelUtil;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.service.*;
import co.tton.qcloud.web.minio.MinioFileService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐商家Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Controller
@RequestMapping("/shop/recommend")
@Api(tags = "推荐商家")
public class TShopRecommendController extends BaseController
{
    private String prefix = "recommend/shop";

    @Autowired
    private ITShopRecommendService tShopRecommendService;

    @Autowired
    private ITShopService tShopService;

    @RequiresPermissions("shop:recommend:list")
    @GetMapping()
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public String courses(ModelMap mmap)
    {
        SysUser user = ShiroUtils.getSysUser();
        if (StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")) {
            mmap.put("regionId", user.getBusinessId());
        }
        return prefix + "/list";
    }

    /**
     * 查询推荐商家列表
     */
    @RequiresPermissions("shop:recommend:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取推荐商家信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public TableDataInfo list(TShopRecommendModel tShopRecommendModel)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShopRecommendModel.setRegionId(user.getBusinessId());
        }
        List<TShopRecommendModel> list = tShopRecommendService.selectTShopRecommendList(tShopRecommendModel);

        return getDataTable(list);
    }

    /**
     * 新增推荐商家
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        TShop tShop = new TShop();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShop.setRegionId(user.getBusinessId());
        }
        List<TShop> tShops = tShopService.selectTShopList(tShop);
        mmap.put("shop", tShops);
        return prefix + "/add";
    }

    /**
     * 新增保存推荐商家
     */
    @RequiresPermissions("shop:recommend:add")
    @Log(title = "推荐商家", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增推荐商家信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult addSave(TShopRecommend tShopRecommend)
    {
        TShop tShop = tShopService.selectTShopById(tShopRecommend.getShopId());
        TShopRecommendModel tShopRecommendModel = new TShopRecommendModel();
        tShopRecommendModel.setRegionId(tShop.getRegionId());
        List<TShopRecommendModel> list = tShopRecommendService.selectTShopRecommendList(tShopRecommendModel);
        if(list != null && list.size()>=10){
            return AjaxResult.error("该地区推荐商家已满10个。");
        }
        int count = tShopRecommendService.insertTShopRecommend(tShopRecommend);
        if (count == 1) {
            return AjaxResult.success("推荐商家成功。",count);
        } else {
            return AjaxResult.error("推荐商家失败。");
        }

    }

    /**
     * 修改推荐商家
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        TShop tShop = new TShop();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tShop.setRegionId(user.getBusinessId());
        }
        List<TShop> tShops = tShopService.selectTShopList(tShop);
        mmap.put("shop", tShops);
        TShopRecommend tShopRecommend = tShopRecommendService.selectTShopRecommendById(id);
        mmap.put("tShopRecommend", tShopRecommend);
        return prefix + "/edit";
    }

    /**
     * 修改保存推荐商家
     */
    @RequiresPermissions("shop:recommend:edit")
    @Log(title = "推荐商家", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改推荐商家信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult editSave(TShopRecommend tShopRecommend)
    {
        return toAjax(tShopRecommendService.updateTShopRecommend(tShopRecommend));
    }


    /**
     * 删除推荐商家
     */
    @RequiresPermissions("shop:recommend:remove")
    @Log(title = "推荐商家", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除推荐商家信息")
    @RoleScope(roleDefined={"ADMIN","REGION"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopRecommendService.deleteTShopRecommendByIds(ids));
    }

}
