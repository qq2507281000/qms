package co.tton.qcloud.web.controller.order;

import java.util.List;

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
import co.tton.qcloud.system.model.OrderConfirmModel;
import co.tton.qcloud.system.service.ITOrderDetailService;
import co.tton.qcloud.system.service.ITOrderUseLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import co.tton.qcloud.system.service.ITOrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单信息Controller
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Api(value="订单管理",tags = "订单管理")
@Controller
@RequestMapping("/order")
public class TOrderController extends BaseController
{
    private String prefix = "order/base";

    @Autowired
    private ITOrderService tOrderService;

    @Autowired
    private ITOrderDetailService orderDetailService;

    @Autowired
    private ITOrderUseLogService orderUseLogService;

    @RequiresPermissions("order:view")
    @GetMapping()
    public String order(@RequestParam(value = "shop-id",required = false) String shopId, ModelMap mmap)
    {
        mmap.put("shopId", "");
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            mmap.put("shopId",user.getBusinessId());
        }
        else{
            if(StringUtils.isNotEmpty(shopId)){
                mmap.put("shopId", shopId);
            }
        }
        if (StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")) {
            mmap.put("regionId", user.getBusinessId());
        }

        return prefix + "/list";
    }

    /**
     * 查询订单信息列表
     */
    @RequiresPermissions("order:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("获取订单列表")
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public TableDataInfo list(@RequestParam(value="shop-id",required = false) String shopId, @ApiParam("订单实体对象") TOrder tOrder)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"SHOP")){
            tOrder.setShopId(user.getBusinessId());
        }
        else if(StringUtils.equalsAnyIgnoreCase(user.getCategory(),"REGION")){
            tOrder.setRegionId(user.getBusinessId());
        }
        else{
            if(StringUtils.isNotEmpty(shopId)){
                tOrder.setShopId(shopId);
            }
        }

        List<TOrder> list = tOrderService.selectTOrderList(tOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @RequiresPermissions("order:export")
    @PostMapping("/export")
    @ResponseBody
    @ApiOperation("导出订单信息列表")
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public AjaxResult export(@ApiParam("订单实体对象") TOrder tOrder)
    {
        List<TOrder> list = tOrderService.selectTOrderList(tOrder);
        ExcelUtil<TOrder> util = new ExcelUtil<TOrder>(TOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /***
     * 确认
     * @param id
     * @param mmap
     * @return
     */
    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") String id, ModelMap mmap){
        TOrderModel orderModel = tOrderService.selectFullOrderById(id);
        mmap.put("order",orderModel);
        return prefix + "/confirm";
    }

    @PostMapping("/confirm")
    public AjaxResult confirmOrder(OrderConfirmModel model){
        try{
            SysUser user = ShiroUtils.getSysUser();
            if(model == null){
                return error("参数错误，对象不允许为空。");
            }
            else{
                TOrder order = tOrderService.selectTOrderById(model.getId());
                if(order == null){
                    return error("未能搜索到订单数据。");
                }
                else{
                    if(StringUtils.equalsAnyIgnoreCase(order.getPayStatus(),"PAID")){
                        if(StringUtils.equalsAnyIgnoreCase(order.getUseStatus(),"UNUSED")
                        && StringUtils.equalsAnyIgnoreCase(order.getVerifyStatus(),"UNCONFIRM")){

                            TOrderDetail query = new TOrderDetail();
                            query.setOrderId(model.getId());
                            List<TOrderDetail> orderDetails = orderDetailService.selectTOrderDetailList(query);

                            order.setUseStatus("USED");
                            order.setVerifyStatus("CONFIRMED");
                            order.setBillStatus("EVALUATING");
                            order.setUpdateTime(DateUtils.getNowDate());
                            order.setUpdateBy(user.getUserId().toString());
                            tOrderService.updateTOrder(order);

                            TOrderUseLog useLog = new TOrderUseLog();
                            useLog.setId(StringUtils.genericId());
                            useLog.setOrderId(order.getId());
                            useLog.setCoursesId("");
                            useLog.setUseTime(DateUtils.getNowDate());
                            useLog.setMemberId(order.getMemberId());
                            useLog.setChildId("");
                            useLog.setShopId(order.getShopId());
                            useLog.setFlag(Constants.DATA_NORMAL);
                            useLog.setCreateBy(user.getUserId().toString());
                            useLog.setCreateTime(DateUtils.getNowDate());

                            orderUseLogService.insertTOrderUseLog(useLog);

                            return success("核销成功。");

                        }
                        else{
                            return error("订单已使用或已核销。");
                        }
                    }
                    else{
                        return error("订单状态未支付，不允许核销。");
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("订单核销时发生异常。",ex);
            return error("订单核销时发生异常。");
        }
    }

    /**
     * 新增保存订单信息
     */
    @RequiresPermissions("order:add")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("保存订单信息")
    @RoleScope(roleDefined={"ADMIN","REGION","SHOP"})
    public AjaxResult addSave(@ApiParam("订单实体对象") TOrder tOrder)
    {
        return toAjax(tOrderService.insertTOrder(tOrder));
    }

    /**
     * 修改订单信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
//        TOrder tOrder = tOrderService.selectTOrderById(id);
//        mmap.put("tOrder", tOrder);
//        return prefix + "/edit";
        TOrderModel orderModel = tOrderService.selectFullOrderById(id);
        mmap.put("order",orderModel);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单信息
     */
    @RequiresPermissions("order:edit")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("编辑订单信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult editSave(@ApiParam("订单实体对象") TOrder tOrder)
    {
        return toAjax(tOrderService.updateTOrder(tOrder));
    }

    /**
     * 删除订单信息
     */
    @RequiresPermissions("order:remove")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    @ApiOperation("删除订单信息")
    @RoleScope(roleDefined={"ADMIN","SHOP"})
    public AjaxResult remove(String ids)
    {
        return toAjax(tOrderService.deleteTOrderByIds(ids));
    }
}
