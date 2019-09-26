package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TOrder;
import co.tton.qcloud.system.domain.WxOrderDetail;
import co.tton.qcloud.system.service.ITOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:49
 */

@RestController
@RequestMapping("/api/v1.0/order")
@Api(tags = "小程序订单")
public class OrderController extends BaseController {

    @Autowired
    private ITOrderService tOrderService;

    @RequiresPermissions("wx:order:submit")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public AjaxResult submitOrder(){
        //TODO:参数未定义，提交数据需要有实体对象。
        return null;
    }

    /***
     *
     * @param
     * @return
     */
    @RequiresPermissions("wx:order:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    @ApiOperation("获取所有订单列表")
    public AjaxResult getOrderList(){
        List<TOrder> tOrders =tOrderService.getOrderList();
        for(TOrder tOrder:tOrders){
            if(tOrder.getPayStatus() == "UNPAY"){
                tOrder.setWxStatus("未支付");
            }else {
                tOrder.setWxStatus("待使用");
            }
            if(tOrder.getUseStatus() == "USED"){
                tOrder.setWxStatus("待评价");
            }
            if (tOrder.getUseStatus() == "已过期"){
                tOrder.setWxStatus("已过期");
            }
            if(tOrder.getBillStatus() == "已完成"){
                tOrder.setWxStatus("已评价");
            }
        }
        return AjaxResult.success("获取顶级分类成功。",tOrders);
    }

    /***
     *
     * @param orderId
     * @return
     */
    @RequiresPermissions("wx:order:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation("获取订单详情")
    public AjaxResult getOrderDetail(@PathVariable("id")String orderId){
        if (StringUtils.isNotEmpty(orderId)) {
            WxOrderDetail wxOrderDetail = tOrderService.getOrderDetail(orderId);
            return AjaxResult.success("获取顶级分类成功。", wxOrderDetail);
        }else {
            return AjaxResult.error("报错：orderId为空。");
        }
    }

    @RequiresPermissions("wx:order:comment")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public AjaxResult comment(){
        return null;
    }


}
