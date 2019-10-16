package co.tton.qcloud.web.controller.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
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
import co.tton.qcloud.web.config.WxMaConfiguration;
import co.tton.qcloud.web.config.WxMaProperties;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
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

@Api(tags = "小程序会员信息",value = "小程序会员信息")
@RestController
@RequestMapping("/api/v1.0/member")
public class MemberController extends BaseController {

    @Resource
    ITMemberService tMemberService;
    @Resource
    ITMemberBabyService tMemberBabyService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private WxPayService wxService;

    @Autowired
    private ITMemberChargingService memberChargingService;

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
    public AjaxResult getVipPrice(@ApiParam(value = "vip等级",defaultValue = "1") @RequestParam("level") int vipLevel){
        try{
            String result = StringUtils.nvl(configService.getKey(String.format("sys.vip.price.%s",vipLevel)),"0");
            return AjaxResult.success("获取Vip价格成功。",result);
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("获取VIP价格时发生异常。");
            return AjaxResult.error("获取Vip价格时发生异常。",ex);
        }
    }

    @ApiOperation("会员充值")
    @RequestMapping(method = RequestMethod.POST, value = "/vip/charging")
    public AjaxResult vipCharging(MemberChargingModel model){
        try{
            if(model == null){
                return AjaxResult.error("参数不允许为空。");
            }
            else{
                String orderNo = StringUtils.genericOrderNo();
                TMemberCharging memberCharging = new TMemberCharging();
                String id = StringUtils.genericId();
                memberCharging.setId(id);
                memberCharging.setMemberId(model.getMemberId());
                memberCharging.setChargingTime(DateUtils.getNowDate());
                memberCharging.setBeginTime(DateUtils.getNowDate());
                memberCharging.setEndTime(DateUtils.addYears(DateUtils.getNowDate(),1));
                memberCharging.setVipLevel(1);
                memberCharging.setFlag(Constants.DATA_NORMAL);
                memberCharging.setCreateBy(model.getMemberId());
                memberCharging.setCreateTime(DateUtils.getNowDate());
                memberCharging.setPayStatus("UNPAY");
                memberCharging.setOrderNo(orderNo);
                int count = memberChargingService.insertTMemberCharging(memberCharging);
                if(count == 1) {
                    WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
                    request.setVersion("1.0");
                    request.setDeviceInfo("000000000000");
                    request.setBody("VIP会员充值");
                    request.setAttach("VIP充值订单");
                    request.setOutTradeNo(orderNo);
                    request.setTotalFee((int) (model.getPrice()));
                    request.setSpbillCreateIp(IpUtils.getHostIp());
                    request.setTimeStart(DateUtils.dateTimeNow());
                    request.setNotifyUrl(Global.getNotifyUrl());
                    request.setTradeType("JSAPI");
                    request.setOpenid(model.getOpenId());
                    wxService.createOrder(request);
                    return AjaxResult.success("支付中...");
                }
                else{
                    return AjaxResult.error("未能创建VIP会员充值订单。");
                }

            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("获取VIP价格时发生异常。");
            return AjaxResult.error("会员充值失败。",ex);
        }
    }

    /***
     *
     * @param memberId
     * @return
     */
    @ApiOperation("查询会员信息")
//    @RequiresPermissions("wx:member:info")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public AjaxResult getMemberInfo(@RequestParam(value = "id") String memberId){
        if(StringUtil.isNotEmpty(memberId)){
            TMemberModel tMemberModel= tMemberService.getMemberInfo(memberId);
            if(tMemberModel.getUseStatus() == null){
                tMemberModel.setUseStatus("0");
            }
            if(tMemberModel.getPayStatus() == null){
                tMemberModel.setPayStatus("0");
            }
            if(tMemberModel.getBillStatus() == null){
                tMemberModel.setBillStatus("0");
            }
            if(StringUtils.equals(tMemberModel.getAccountLevel(),"")){
                TMemberCharging memberCharging = memberChargingService.selectTMemberChargingByMemberId(memberId);
                if(memberCharging != null){
                    tMemberModel.setVipBeginTime(memberCharging.getBeginTime());
                    tMemberModel.setVipEndTime(memberCharging.getEndTime());
                }
            }
            return AjaxResult.success("获取会员信息成功",tMemberModel);
        }else{
            return AjaxResult.error("会员ID错误");
        }
    }

    /***
     *
     * @param memberId
     * @return
     */
    @ApiOperation("查询会员用户子女信息")
//    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/getFavourite",method = RequestMethod.GET)
    public AjaxResult<TMember> getFavourite(@RequestParam(value = "id") String memberId){
        if(StringUtil.isNotEmpty(memberId)){
            TMember tMember = tMemberService.getFavourite(memberId);
            return AjaxResult.success("获取会员信息成功",tMember);
        }else{
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
    @RequestMapping(value="/upMemberBaby",method = RequestMethod.GET)
    public AjaxResult upMemberBabyInfo(@RequestParam(value = "memberid",required = false) String memberId,
                                       @RequestParam(value = "realname") String realName,
                                       @RequestParam(value = "sex")Integer sex,
                                       @RequestParam(value = "birthday")Date birthday){
        if(StringUtil.isNotEmpty(memberId)){
            //查询会员子女表相关信息
            TMemberBaby tMemberBaby  = tMemberService.getTMemberBabyId(memberId);
            if(StringUtils.isNull(tMemberBaby)){
                TMemberBaby tMemberBaby1 = new TMemberBaby();
                SysUser user = ShiroUtils.getSysUser();
                tMemberBaby1.setCreateBy(user.getUserId().toString());
                tMemberBaby1.setCreateTime(new Date());
                tMemberBaby1.setFlag(Constants.DATA_NORMAL);
                tMemberBaby1.setId(StringUtils.genericId());
                tMemberBaby1.setMemberId(memberId);
                tMemberBaby1.setRealName(realName);
                tMemberBaby1.setSex(sex);
                tMemberBaby1.setBirthday(birthday);
                //没有,调新增
                int number = tMemberBabyService.insertTMemberBaby(tMemberBaby1);
                if(number == 0){
                    return AjaxResult.success("新增会员子女信息失败");
                }else{
                    return AjaxResult.success("新增会员子女信息成功",number);
                }
            }else{
                //有修改
                TMemberBaby tMemberBaby1 = new TMemberBaby();
                tMemberBaby1.setMemberId(memberId);
                tMemberBaby1.setRealName(realName);
                tMemberBaby1.setSex(sex);
                tMemberBaby1.setBirthday(birthday);
                int number = tMemberService.upMemberBabyInfo(tMemberBaby1);
                if(number == 0){
                    return AjaxResult.success("修改会员子女信息失败");
                }else{
                    return AjaxResult.success("修改会员子女信息成功",number);
                }
            }
        }else{
            return AjaxResult.error("会员ID错误");
        }
    }

    /***
     *
     * @param
     * @return
     */
    @ApiOperation("会员年费信息暂时写固定值")
//    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/getMemberYearMoney",method = RequestMethod.GET)
    public AjaxResult<List> upMemberBabyInfo(){
        //会员年费97.00和会员描述暂时写固定值
        //会员年费
        double memberYearMoney = 97.00F;
        //会员描述
        String member ="12月年费超级VIP";
        List memberYearList = new ArrayList();
        memberYearList.add(memberYearMoney);
        memberYearList.add(member);
        return AjaxResult.success("修改会员子女信息成功",memberYearList);
    }

    @ApiOperation("根据openId查询会员信息")
//    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/getMemberByOpenId",method = RequestMethod.GET)
    public AjaxResult<TMember> getMemberByOpenId(@RequestParam(value = "openId") String openId){
        if(StringUtils.isNotEmpty(openId)){
            TMember tMember = tMemberService.getMemberByOpenId(openId);
            if(tMember == null){
                return AjaxResult.error("错误：不存在该用户");
            }
            else {
                return AjaxResult.success("查询会员信息成功",tMember);
            }
        }
        else{
            return AjaxResult.error("错误：openId为空");
        }
    }


//    @RequiresPermissions("wx:feedback")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    @ApiOperation("新增用户信息")
    public AjaxResult<TMember> saveMember(TMember tMember){
        if(StringUtils.isNotNull(tMember)){
            SysUser user = ShiroUtils.getSysUser();
            tMember.setId(StringUtils.genericId());
            tMember.setAccountLevel("0");
            tMember.setCreateBy(user.getUserId().toString());
            tMember.setCreateTime(new Date());
            tMember.setFlag(Constants.DATA_NORMAL);
            int number = tMemberService.saveMember(tMember);
            if(number == 0){
                return AjaxResult.success("新增用户失败。");
            }else{
                return AjaxResult.success("新增用户成功。",number);
            }
        }else{
            return AjaxResult.error("参数错误。");
        }


    }

    //    @RequiresPermissions("wx:mobile")
    @RequestMapping(value = "/mobile",method = RequestMethod.POST)
    @ApiOperation("绑定手机号")
    public AjaxResult<TMember> upMobile(@RequestParam(value = "memberid") String memberid,
                                        @RequestParam(value = "mobile") String mobile){
        if(StringUtils.isNotEmpty(memberid)&&StringUtils.isNotEmpty(mobile)){
            TMember tMember = new TMember();
            tMember.setId(memberid);
            tMember.setMobile(mobile);
            int number = tMemberService.upMobile(tMember);
            if(number > 0){
                return AjaxResult.success("保存用户成功。",number);
            }else{
                return AjaxResult.success("修改失败。");
            }
        }else{
            return AjaxResult.error("参数错误。");
        }
    }
}
