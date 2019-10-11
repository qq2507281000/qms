package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TOrder;
import co.tton.qcloud.system.domain.TOrderModel;
import co.tton.qcloud.system.domain.WxOrderDetail;
import co.tton.qcloud.system.service.ITOrderService;
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
 * @create: 2019-09-18 18:49
 */

@RestController
@RequestMapping("/api/v1.0/order")
@Api(tags = "小程序订单")
public class OrderController extends BaseController {

    @Autowired
    private ITOrderService tOrderService;

//    @RequiresPermissions("wx:order:submit")
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
//    @RequiresPermissions("wx:order:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    @ApiOperation("获取所有订单列表,支付状态订单列表")
    public AjaxResult getOrderList(@RequestParam(value = "memberid")String memberId,
                                   @RequestParam(value = "billstatus",required = false)String billStatus){
        TOrder tOrderNew = new TOrder();
        tOrderNew.setBillStatus(billStatus);
        tOrderNew.setMemberId(memberId);
        List<TOrder> tOrders =tOrderService.getOrderList(tOrderNew);
        for(TOrder tOrder:tOrders){
            switch (tOrder.getBillStatus()) {
                case "BOOKING":
                    tOrder.setBillStatus("下单中");
                    break;
                case "EXECUTING":
                    tOrder.setBillStatus("执行中");
                    break;
                case "EVALUATING":
                    tOrder.setBillStatus("评价中");
                    break;
                case "FINISHED":
                    tOrder.setBillStatus("已完成");
                    break;
            }
        }
        return AjaxResult.success("获取顶级分类成功。",tOrders);
    }

    /***
     *
     * @param orderId
     * @return
     */
//    @RequiresPermissions("wx:order:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation("获取订单详情")
    public AjaxResult getOrderDetail(@PathVariable("id")String orderId){
        if (StringUtils.isNotEmpty(orderId)) {
            WxOrderDetail wxOrderDetail = tOrderService.getOrderDetail(orderId);
            if (wxOrderDetail == null){
                return AjaxResult.error("报错：对象为空。");
            }else {
                return AjaxResult.success("获取顶级分类成功。", wxOrderDetail);
            }
        }else {
            return AjaxResult.error("报错：orderId为空。");
        }
    }

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:order:count")
    @RequestMapping(value="/countOder",method = RequestMethod.GET)
    @ApiOperation("根据订单状态获取订单数量")
    public AjaxResult getCountOrder(@RequestParam(value = "billstatus")String billStatus,
                                    @RequestParam(value = "memberid")String memberId){
        if(StringUtils.isNotEmpty(billStatus)){
            TOrderModel tOrder = new TOrderModel();
            tOrder.setBillStatus(billStatus);
            tOrder.setMemberId(memberId);
            TOrderModel tOrderModel =tOrderService.getCountOrder(tOrder);
            return AjaxResult.success("获取数量成功。",tOrderModel);
        }else{
            return AjaxResult.error("参数错误");
        }
    }

//    @RequiresPermissions("wx:order:comment")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public AjaxResult comment(){
        return null;
    }


}
