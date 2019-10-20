package co.tton.qcloud.web.controller.wx;

import cn.hutool.core.date.DateUtil;
import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.IpUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.framework.web.service.ConfigService;
import co.tton.qcloud.system.domain.*;
import co.tton.qcloud.system.model.MemberChargingModel;
import co.tton.qcloud.system.service.ITMemberBabyService;
import co.tton.qcloud.system.service.ITMemberChargingService;
import co.tton.qcloud.system.wxservice.ITMemberService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:34
 */

@Api(tags = "小程序会员信息", value = "小程序会员信息")
@RestController
@RequestMapping("/api/v1.0/member")
public class MemberController extends BaseController {

  @Resource
  ITMemberService tMemberService;

  @Resource
  ITMemberBabyService tMemberBabyService;

  @Autowired
  private ITMemberService itMemberService;

  @Autowired
  private ConfigService configService;

  @Autowired
  private WxPayService wxService;

  @Autowired
  private ITMemberChargingService memberChargingService;

  @Autowired
  private co.tton.qcloud.system.service.ITMemberService memberService;

  @Autowired
  private co.tton.qcloud.system.service.ITMemberBabyService memberBabyService;


//    @RequestMapping("/login")
//    private AjaxResult getOpenId(@RequestParam("code") String code){
//        try{
//            String appId = Global.getConfig("qcloud.active-miniapp-appId");
//            if(StringUtils.isEmpty(appId)){
//                return AjaxResult.error("微信小程序配置错误[AppId为空]。");
//            }
//            else{
//
//            }
//            final WxMaService wxService = WxMaConfiguration.getMaService(appId);
//            if(wxService != null){
//                WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
//                if(session != null){
//                    String openId = session.getOpenid();
//                    String sessionKey = session.getSessionKey();
//                    String unionId = session.getUnionid();
//                    //根据openId到数据库中搜索，如果未找到，则获取用户信息，添加到数据库中。
//                    TMember member = tMemberService.getMemberByOpenId(openId);
//                    if(member == null){
//
//                    }
//                    //如果存在，则直接获取用户信息返回至前端。
//
//                    //生成登录Token。
//
//                }
//                else{
//                    return AjaxResult.error("未能获取JSSDK返回结果。");
//                }
//            }
//            else{
//                return AjaxResult.error("微信小程序操作类实例化失败。");
//            }
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//            logger.error("会员登录",ex);
//            return AjaxResult.error("会员登录失败。",ex);
//        }
//    }

  @ApiOperation("获取VIP价格")
  @RequestMapping("/vip/price/{level}")
  public AjaxResult getVipPrice(@ApiParam(value = "vip等级", defaultValue = "1") @RequestParam("level") int vipLevel) {
    try {
      String result = StringUtils.nvl(configService.getKey(String.format("sys.vip.price.%s", vipLevel)), "0");
      return AjaxResult.success("获取Vip价格成功。", result);
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("获取VIP价格时发生异常。");
      return AjaxResult.error("获取Vip价格时发生异常。", ex);
    }
  }

  @ApiOperation("会员充值回调接口")
  @RequestMapping(value = "/vip/charging/notify",method = RequestMethod.POST)
  public AjaxResult parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
    try{
      final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);
      String orderNo = notifyResult.getOutTradeNo();
      String transactionId = notifyResult.getTransactionId();
      String payTime = notifyResult.getTimeEnd();
      Date date = DateUtil.parse(payTime,"yyyyMMddHHmmss");
      TMemberCharging memberCharging = memberChargingService.selectTMemberChargingByOrderNo(orderNo);
      if(memberCharging != null){
        memberCharging.setChargingTime(date);
        memberCharging.setBeginTime(date);
        memberCharging.setEndTime(DateUtils.addYears(date,1));
        memberCharging.setSerialNo(transactionId);
        memberCharging.setPayStatus("PAID");
        int result = memberChargingService.updateTMemberCharging(memberCharging);
        if(result == 1){
          return AjaxResult.success("会员充值回调成功。");
        }
        else{
          return AjaxResult.error("会员充值回调失败。");
        }
      }
      else{
        return AjaxResult.error("未能找到会员充值信息。");
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      logger.error("订单支付回调接口异常。");
      return error("订单支付回调接口异常。\r\n" + ex.getMessage());
    }
  }

  @ApiOperation("会员充值")
  @RequestMapping(method = RequestMethod.POST, value = "/vip/charging")
  public AjaxResult vipCharging(@RequestBody MemberChargingModel model) {
    try {
      if (model == null) {
        return AjaxResult.error("参数不允许为空。");
      } else {
        String orderNo = StringUtils.genericOrderNo();
        TMemberCharging memberCharging = new TMemberCharging();
        String id = StringUtils.genericId();
        memberCharging.setId(id);
        memberCharging.setMemberId(model.getMemberId());
        memberCharging.setChargingTime(DateUtils.getNowDate());
        memberCharging.setBeginTime(DateUtils.getNowDate());
        memberCharging.setEndTime(DateUtils.addYears(DateUtils.getNowDate(), 1));
        memberCharging.setVipLevel(1);
        memberCharging.setFlag(Constants.DATA_NORMAL);
        memberCharging.setCreateBy(model.getMemberId());
        memberCharging.setCreateTime(DateUtils.getNowDate());
        memberCharging.setPayStatus("UNPAY");
        memberCharging.setOrderNo(orderNo);
        int count = memberChargingService.insertTMemberCharging(memberCharging);
        if (count == 1) {
          WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
          request.setVersion("1.0");
          request.setDeviceInfo("000000000000");
          request.setBody("VIP会员充值");
          request.setAttach("VIP充值订单");
          request.setOutTradeNo(orderNo);
          request.setTotalFee((int) (model.getPrice()));
          request.setSpbillCreateIp(IpUtils.getHostIp());
          request.setTimeStart(DateUtils.dateTimeNow());
          request.setNotifyUrl(Global.getChargingPayNotifyUrl());
          request.setTradeType("JSAPI");
          request.setOpenid(model.getOpenId());
          WxPayMpOrderResult orderResult = wxService.createOrder(request);
          return AjaxResult.success("支付中...",orderResult);
        } else {
          return AjaxResult.error("未能创建VIP会员充值订单。");
        }

      }
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error("会员充值失败。",ex);
      return AjaxResult.error("会员充值失败。", ex);
    }
  }

  /***
   *
   * @param memberId
   * @return
   */
  @ApiOperation("查询会员信息")
//    @RequiresPermissions("wx:member:info")
  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public AjaxResult getMemberInfo(@RequestParam(value = "id") String memberId) {
    if (StringUtil.isNotEmpty(memberId)) {
//            TMember member = memberService.selectTMemberById(memberId);
      TMemberModel member = itMemberService.getMemberInfo(memberId);
      if (member == null) {
        return AjaxResult.error("未能找到会员信息。");
      } else {
        if (member.getFlag() == Constants.DATA_NORMAL) {
          if (StringUtils.equals(member.getAccountLevel(), "超级会员")) {
            TMemberCharging memberCharging = memberChargingService.selectTMemberChargingByMemberId(memberId);
            if (memberCharging != null) {
              member.setVipBeginTime(memberCharging.getBeginTime());
              member.setVipEndTime(memberCharging.getEndTime());
            }
          }
          return AjaxResult.success("获取会员信息成功。", member);
        } else {
          return AjaxResult.error("会员信息已被删除。");
        }
      }

      //            TMemberModel tMemberModel= tMemberService.getMemberInfo(memberId);
//            if(tMemberModel.getUseStatus() == null){
//                tMemberModel.setUseStatus("0");
//            }
//            if(tMemberModel.getPayStatus() == null){
//                tMemberModel.setPayStatus("0");
//            }
//            if(tMemberModel.getBillStatus() == null){
//                tMemberModel.setBillStatus("0");
//            }
//            if(StringUtils.equals(tMemberModel.getAccountLevel(),"")){
//                TMemberCharging memberCharging = memberChargingService.selectTMemberChargingByMemberId(memberId);
//                if(memberCharging != null){
//                    tMemberModel.setVipBeginTime(memberCharging.getBeginTime());
//                    tMemberModel.setVipEndTime(memberCharging.getEndTime());
//                }
//            }
//            return AjaxResult.success("获取会员信息成功",tMemberModel);
    } else {
      return AjaxResult.error("参数错误，会员Id不允许为空。");
    }
  }

  /***
   *
   * @param memberId
   * @return
   */
  @ApiOperation("查询会员用户子女信息")
//    @RequiresPermissions("wx:member:orders")
  @RequestMapping(value = "/getFavourite", method = RequestMethod.GET)
  public AjaxResult<TMember> getFavourite(@RequestParam(value = "id") String memberId) {
    if (StringUtil.isNotEmpty(memberId)) {
      TMember tMember = tMemberService.getFavourite(memberId);
      return AjaxResult.success("获取会员信息成功", tMember);
    } else {
      return AjaxResult.error("会员ID错误");
    }
  }

  /***
   *
   * @param realName,sex,birthday
   * @return
   */
  @ApiOperation("会员子女信息修改")
//    @RequiresPermissions("wx:member:orders")
  @RequestMapping(value = "/upMemberBaby", method = RequestMethod.GET)
  public AjaxResult upMemberBabyInfo(@RequestParam(value = "memberid") String memberId,
                                     @RequestParam(value = "realname", required = false) String realName,
                                     @RequestParam(value = "sex", required = false) Integer sex,
                                     @RequestParam(value = "birthday", required = false) Date birthday) {
    if (StringUtil.isNotEmpty(memberId)) {
      //查询会员子女表相关信息
      TMemberBaby memberBaby = new TMemberBaby();
      memberBaby.setMemberId(memberId);

      int result = 0;

      TMemberBaby baby = null;

      List<TMemberBaby> list = memberBabyService.selectTMemberBabyList(memberBaby);
      if (list != null) {
//                baby = list.stream().filter(d->StringUtils.equals(d.getRealName(),realName))
//                        .findFirst().orElse(null);
        if (list.size() >= 1) {
          baby = list.get(0);
          baby.setRealName(realName);
          baby.setSex(sex);
          baby.setBirthday(birthday);
          baby.setUpdateBy(memberId);
          baby.setUpdateTime(DateUtils.getNowDate());
          result = memberBabyService.updateTMemberBaby(baby);
        } else {
          String id = StringUtils.genericId();
          baby = new TMemberBaby();
          baby.setId(id);
          baby.setMemberId(memberId);
          baby.setRealName(realName);
          baby.setSex(sex);
          baby.setBirthday(birthday);
          baby.setFlag(Constants.DATA_NORMAL);
          baby.setCreateBy(memberId);
          baby.setCreateTime(DateUtils.getNowDate());
          result = memberBabyService.insertTMemberBaby(baby);
        }
      } else {
        String id = StringUtils.genericId();
        baby = new TMemberBaby();
        baby.setId(id);
        baby.setMemberId(memberId);
        baby.setRealName(realName);
        baby.setSex(sex);
        baby.setBirthday(birthday);
        baby.setFlag(Constants.DATA_NORMAL);
        baby.setCreateBy(memberId);
        baby.setCreateTime(DateUtils.getNowDate());
        result = memberBabyService.insertTMemberBaby(baby);
      }

      if (result == 1) {
        return AjaxResult.success("新增或修改会员宝宝信息成功。", baby);
      } else {
        return AjaxResult.error("新增活修改会员宝宝信息失败。");
      }

//            TMemberBaby tMemberBaby  = tMemberService.getTMemberBabyId(memberId);
//            if(StringUtils.isNull(tMemberBaby)){
//                TMemberBaby tMemberBaby1 = new TMemberBaby();
//                SysUser user = ShiroUtils.getSysUser();
//                tMemberBaby1.setCreateBy(user.getUserId().toString());
//                tMemberBaby1.setCreateTime(new Date());
//                tMemberBaby1.setFlag(Constants.DATA_NORMAL);
//                tMemberBaby1.setId(StringUtils.genericId());
//                tMemberBaby1.setMemberId(memberId);
//                tMemberBaby1.setRealName(realName);
//                tMemberBaby1.setSex(sex);
//                tMemberBaby1.setBirthday(birthday);
//                //没有,调新增
//                int number = tMemberBabyService.insertTMemberBaby(tMemberBaby1);
//                if(number == 0){
//                    return AjaxResult.success("新增会员子女信息失败");
//                }else{
//                    return AjaxResult.success("新增会员子女信息成功",number);
//                }
//            }else{
//                //有修改
//                TMemberBaby tMemberBaby1 = new TMemberBaby();
//                tMemberBaby1.setMemberId(memberId);
//                tMemberBaby1.setRealName(realName);
//                tMemberBaby1.setSex(sex);
//                tMemberBaby1.setBirthday(birthday);
//                int number = tMemberService.upMemberBabyInfo(tMemberBaby1);
//                if(number == 0){
//                    return AjaxResult.success("修改会员子女信息失败");
//                }else{
//                    return AjaxResult.success("修改会员子女信息成功",number);
//                }
//            }
    } else {
      return AjaxResult.error("参数错误，会员Id不允许为空。");
    }
  }

  /***
   *
   * @param
   * @return
   */
  @ApiOperation("会员年费信息暂时写固定值")
//    @RequiresPermissions("wx:member:orders")
  @RequestMapping(value = "/getMemberYearMoney", method = RequestMethod.GET)
  public AjaxResult<List> upMemberBabyInfo() {
    //会员年费97.00和会员描述暂时写固定值
    //会员年费
    double memberYearMoney = 97.00F;
    //会员描述
    String member = "12月年费超级VIP";
    List memberYearList = new ArrayList();
    memberYearList.add(memberYearMoney);
    memberYearList.add(member);
    return AjaxResult.success("修改会员子女信息成功", memberYearList);
  }

  @ApiOperation("根据openId查询会员信息")
//    @RequiresPermissions("wx:member:orders")
  @RequestMapping(value = "/getMemberByOpenId", method = RequestMethod.GET)
  public AjaxResult<TMember> getMemberByOpenId(@RequestParam(value = "openId") String openId) {
    if (StringUtils.isNotEmpty(openId)) {
      TMember tMember = tMemberService.getMemberByOpenId(openId);
      if (tMember == null) {
        return AjaxResult.error("错误：不存在该用户");
      } else {
        return AjaxResult.success("查询会员信息成功", tMember);
      }
    } else {
      return AjaxResult.error("错误：openId为空");
    }
  }


  //    @RequiresPermissions("wx:feedback")
  @RequestMapping(value = "/info", method = RequestMethod.POST)
  @ApiOperation("新增用户信息")
  public AjaxResult<TMember> saveMember(TMember tMember) {
    if (StringUtils.isNotNull(tMember)) {
      SysUser user = ShiroUtils.getSysUser();
      tMember.setId(StringUtils.genericId());
      tMember.setAccountLevel("0");
      tMember.setCreateBy(user.getUserId().toString());
      tMember.setCreateTime(new Date());
      tMember.setFlag(Constants.DATA_NORMAL);
      int number = tMemberService.saveMember(tMember);
      if (number == 0) {
        return AjaxResult.success("新增用户失败。", number);
      } else {
        return AjaxResult.success("新增用户成功。", number);
      }
    } else {
      return AjaxResult.error("参数错误。");
    }


  }

  //    @RequiresPermissions("wx:mobile")
  @RequestMapping(value = "/mobile", method = RequestMethod.POST)
  @ApiOperation("绑定手机号")
  public AjaxResult<TMember> upMobile(@RequestParam(value = "memberid") String memberid,
                                      @RequestParam(value = "mobile") String mobile) {

    if (StringUtils.isNotEmpty(memberid) && StringUtils.isNotEmpty(mobile)) {
      TMember tMember = memberService.selectTMemberById(memberid);
      if (tMember == null) {
        return AjaxResult.error("未能找到会员信息。");
      } else {
        tMember.setMobile(mobile);
        tMember.setUpdateBy(memberid);
        tMember.setUpdateTime(DateUtils.getNowDate());
        int result = memberService.updateTMember(tMember);
        if (result == 1) {
          return AjaxResult.success("用户绑定手机号码成功。", tMember);
        } else {
          return AjaxResult.error("用户绑定手机号码失败。");
        }
      }
    } else {
      return AjaxResult.error("参数错误。");
    }
  }
}
