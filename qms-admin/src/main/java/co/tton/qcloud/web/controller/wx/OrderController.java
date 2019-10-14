package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.core.page.TableDataInfo;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.service.ITOrderService;
import co.tton.qcloud.system.wxservice.ITOrderUseEvaluationService;
import co.tton.qcloud.web.minio.MinioFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
            if(StringUtils.isNotEmpty(billStatus)){
                tOrderNew.setBillStatus(billStatus);
            }
            if(StringUtils.isNotEmpty(payStatus)){
                tOrderNew.setPayStatus(payStatus);
            }
            if(StringUtils.isNotEmpty(useStatus)){
                tOrderNew.setUseStatus(useStatus);
            }
            List<TOrder> tOrders =tOrderService.getOrderList(tOrderNew);
            if(StringUtils.isNotNull(tOrders)){
                return AjaxResult.success("获取分类成功。",tOrders);
            }else{
                return AjaxResult.success("获取分类失败。");
            }
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
    @RequestMapping(value = "/evaluation",method = RequestMethod.POST)
    @ApiOperation("订单和课程评价")
    public AjaxResult insertOrderUseEvaluation(@RequestParam(value = "orderno")String orderNo,
                                               @RequestParam(value = "memberid")String memberId,
                                               @RequestParam(value = "imageurl")String imageUrl,
                                               @RequestParam(value = "evaluation")String evaluation,
                                               @RequestParam(value = "coursesid")String coursesId,
                                               @RequestParam(value = "star")String star){

        if(StringUtils.isNotEmpty(memberId)){
            TOrderUseEvaluation tOrder = new TOrderUseEvaluation();

//            if(tOrder.getParams().containsKey("file")){
            //调用上传图片接口

//                MultipartFile file = (MultipartFile)tOrder.getParams().get("file");
//                if(file != null){
//                    String fileName = minioFileService.upload(file);
//                    tOrder.setImageUrl(fileName);
            tOrder.setOrderNo(orderNo);
            tOrder.setOrderNo(coursesId);
            String id = StringUtils.genericId();
            tOrder.setId(id);
            tOrder.setFlag(Constants.DATA_NORMAL);
            tOrder.setMemberId(memberId);
            tOrder.setImageUrl(imageUrl);
            tOrder.setEvaluation(evaluation);
            if(!StringUtils.isNotEmpty(star)){
                tOrder.setStar("0");
            }
            tOrder.setStar(star);
            int number = tOrderUseEvaluationService.insertOrderUseEvaluation(tOrder);
            if(number>0){
                return AjaxResult.success("数据保存成功。",number);
            }else{
                return AjaxResult.success("数据保存失败。");
            }
        }else{
            return AjaxResult.error("参数错误");
        }
    }

    /***
     *
     * @param
     * @return
     */
//    @RequiresPermissions("wx:order:billStatus")
    @RequestMapping(value="/courses/billStatus/category",method = RequestMethod.GET)
    @ApiOperation("课程下单时选择的类别")
    public AjaxResult<List<TOrder>> getBillStatusCourses(@RequestParam(value = "memberid")String memberId){
        if(StringUtils.isNotEmpty(memberId)){
            TOrder tOrder = new TOrder();
            tOrder.setMemberId(memberId);
            List<TOrderModel> tOrderModel = tOrderService.getBillStatusCourses(tOrder);
            if(StringUtils.isNotEmpty(tOrderModel)){
                return AjaxResult.success("获取分类成功。",tOrderModel);
            }else {
                return AjaxResult.success("获取分类失败。");
            }
        }else{
            return AjaxResult.error("参数错误。");
        }

    }


    @RequiresPermissions("wx:order:comment")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public AjaxResult comment(){
        return null;
    }


}
