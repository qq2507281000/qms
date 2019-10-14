package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.IpUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.model.OrderEvaluationModel;
import co.tton.qcloud.system.model.OrderModel;
import co.tton.qcloud.system.model.OrderPayModel;
import co.tton.qcloud.system.service.ITOrderService;
import co.tton.qcloud.system.service.ITShopCoursesService;
import co.tton.qcloud.system.service.ITShopService;
import co.tton.qcloud.system.wxservice.ITOrderUseEvaluationService;
import co.tton.qcloud.web.minio.MinioFileService;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private MinioFileService minioFileService;
    @Autowired
    private ITOrderUseEvaluationService tOrderUseEvaluationService;

//    @RequiresPermissions("wx:order:submit")
//    @RequestMapping(value = "",method = RequestMethod.POST)
//    public AjaxResult submitOrder(){
//        //TODO:参数未定义，提交数据需要有实体对象。
//        return null;
//    }

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:order:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    @ApiOperation("获取所有订单列表,根据状态获取订单列表")
    public AjaxResult<List<TOrder>> getOrderList(@RequestParam(value = "memberid")String memberId,
                                                 @RequestParam(value = "billstatus",required = false)String billStatus,
                                                 @RequestParam(value = "paystatus",required = false)String payStatus,
                                                 @RequestParam(value = "usestatus",required = false)String useStatus){
        if(StringUtils.isNotEmpty(memberId)){
            TOrder tOrderNew = new TOrder();
            tOrderNew.setMemberId(memberId);
            if(StringUtils.isNotEmpty(billStatus)&&billStatus.equals("FINISHED")){
                tOrderNew.setBillStatus(billStatus);
            }
            if(StringUtils.isNotEmpty(payStatus)&&payStatus.equals("UNPAY")){
                tOrderNew.setPayStatus(payStatus);
            }
            if(StringUtils.isNotEmpty(useStatus)&&useStatus.equals("UNUSED")){
                tOrderNew.setUseStatus(useStatus);
            }
            List<TOrder> tOrders =tOrderService.getOrderList(tOrderNew);
            for(TOrder tOrder:tOrders) {
                if (tOrder.getPayStatus().equals("UNPAY")) {
                    tOrder.setWxStatus("未支付");
                } else {
                    tOrder.setWxStatus("待使用");
                }
                if (tOrder.getUseStatus().equals("USED")) {
                    tOrder.setWxStatus("待评价");
                }
                if (tOrder.getUseStatus().equals("EXPIRED")) {
                    tOrder.setWxStatus("已过期");
                }
                if (tOrder.getBillStatus().equals("FINISHED")) {
                    tOrder.setWxStatus("已评价");
                }
            }
            return AjaxResult.success("获取分类成功。",tOrders);
        }else{
            return AjaxResult.error("参数错误");
        }
    }

    /***
     *
     * @param orderId
     * @return
     */
//    @RequiresPermissions("wx:order:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation("获取订单详情")
    public AjaxResult<WxOrderDetail> getOrderDetail(@PathVariable("id")String orderId){
        if (StringUtils.isNotEmpty(orderId)) {
            WxOrderDetail wxOrderDetail = tOrderService.getOrderDetail(orderId);
            if (wxOrderDetail == null){
                return AjaxResult.error("报错：对象为空。");
            }else {
                return AjaxResult.success("获取订单详情成功。", wxOrderDetail);
            }
        }else {
            return AjaxResult.error("报错：orderId为空。");
        }
    }

    //    @RequiresPermissions("wx:evaluation:insert")
//    不好使
    @RequestMapping(value = "/evaluation",method = RequestMethod.GET)
    @ApiOperation("订单评价包含订单评价图片-----不好使")
    public AjaxResult insertOrderUseEvaluation(@RequestParam(value = "orderno")String orderNo,
                                               @RequestParam(value = "memberid")String memberId,
                                               @RequestParam(value = "imageurl")String imageUrl,
                                               @RequestParam(value = "evaluation")String evaluation,
                                               @RequestParam(value = "star")String star){
        try {
            TOrderUseEvaluation tOrder = new TOrderUseEvaluation();
            String id = StringUtils.genericId();
            tOrder.setId(id);
            if(tOrder.getParams().containsKey("file")){
                //有新文件上传
                MultipartFile file = (MultipartFile)tOrder.getParams().get("file");
                if(file != null){
                    String fileName = minioFileService.upload(file);
                    tOrder.setImageUrl(fileName);
                    tOrder.setFlag(Constants.DATA_NORMAL);
                    tOrder.setOrderNo(orderNo);
                    tOrder.setMemberId(memberId);
                    tOrder.setImageUrl(imageUrl);
                    tOrder.setEvaluation(evaluation);
                    tOrder.setStar(star);
                    int number = tOrderUseEvaluationService.insertOrderUseEvaluation(tOrder);
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
            logger.error("保存图片时发生异常。",ex);
            return AjaxResult.error("保存图片时发生异常。");
        }
    }




    @RequiresPermissions("wx:order:comment")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public AjaxResult comment(){
        return null;
    }


}
