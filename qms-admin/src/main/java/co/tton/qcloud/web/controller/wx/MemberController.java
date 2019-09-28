package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.domain.TMemberModel;
import co.tton.qcloud.system.wxservice.ITMemberService;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /***
     *
     * @param memberId
     * @return
     */
    @ApiOperation("查询会员信息")
    @RequiresPermissions("wx:member:info")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public AjaxResult<TMemberModel> getMemberInfo(@RequestParam(value = "id") String memberId){
        if(StringUtil.isNotEmpty(memberId)){
            TMemberModel tMemberModel= tMemberService.getMemberInfo(memberId);
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
    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/getFavourite",method = RequestMethod.GET)
    public AjaxResult<TMember> getFavourite(@RequestParam(value = "id") String memberId){
        if(StringUtil.isNotEmpty(memberId)){
            List<TMember> tMemberList = tMemberService.getFavourite(memberId);
            return AjaxResult.success("获取会员信息成功",tMemberList);
        }else{
            return AjaxResult.error("会员ID错误");
        }
    }

    /***
     *
     * @param memberBabyId,realName,sex,birthday
     * @return
     */
    @ApiOperation("会员子女信息修改")
    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/upMemberBaby",method = RequestMethod.GET)
    public AjaxResult upMemberBabyInfo(@RequestParam(value = "id") String memberBabyId,
                                           @RequestParam(value = "realName") String realName,
                                           @RequestParam(value = "sex")Integer sex,
                                           @RequestParam(value = "birthday")String birthday){
        if(StringUtil.isNotEmpty(memberBabyId)){
                int number = tMemberService.upMemberBabyInfo(memberBabyId,realName,sex,birthday);
                return AjaxResult.success("修改会员子女信息成功",number);
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
    @RequiresPermissions("wx:member:orders")
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

}
