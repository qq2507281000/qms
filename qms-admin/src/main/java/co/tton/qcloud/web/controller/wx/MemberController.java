package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.framework.util.ShiroUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.domain.TMemberBaby;
import co.tton.qcloud.system.domain.TMemberModel;
import co.tton.qcloud.system.service.ITMemberBabyService;
import co.tton.qcloud.system.wxservice.ITMemberService;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
