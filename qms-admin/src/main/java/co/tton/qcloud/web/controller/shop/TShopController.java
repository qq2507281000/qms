package co.tton.qcloud.web.controller.shop;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import co.tton.qcloud.common.annotation.Log;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.enums.BusinessType;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private MinioFileService minioFileService;

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
    public AjaxResult addSave(TShop tShop)
    {
        try {
            SysUser currentUser = ShiroUtils.getSysUser();
            String id = StringUtils.genericId();
            tShop.setId(id);

            if(tShop.getParams().containsKey("file")){
                //有新文件上传
                MultipartFile file = (MultipartFile)tShop.getParams().get("file");
                if(file != null){
                    String fileName = minioFileService.upload(file);
                    tShop.setCoverImg(fileName);
                    tShop.setCreateBy(currentUser.getUserId().toString());
                    tShop.setCreateTime(DateUtil.date());
                    int number=tShopService.insertTShop(tShop);
                    return AjaxResult.success("数据保存成功。",number);
                }
                else{
                    return AjaxResult.error("未能获取上传文件内容。");
                }
            }
            else{
                return AjaxResult.error("请选择图片上传。");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("保存滚动广告图片时发生异常。",ex);
            return AjaxResult.error("保存滚动广告图片时发生异常。");
        }
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
    public AjaxResult editSave(TShop tShop)
    {
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
    public AjaxResult remove(String ids)
    {
        return toAjax(tShopService.deleteTShopByIds(ids));
    }
}
