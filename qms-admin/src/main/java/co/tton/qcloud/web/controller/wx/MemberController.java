package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.wxservice.ITMemberService;
import com.github.pagehelper.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:34
 */

@Api(tags = "小程序会员信息",value = "小程序微信会员信息")
@RestController
@RequestMapping("/api/v1.0/member")
public class MemberController extends BaseController {

    @Resource
    ITMemberService tMemberSrevice;

    @ApiOperation("查询会员信息")
    @RequiresPermissions("wx:member:info")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public AjaxResult getMemberInfo(@RequestParam(value = "id") String memberId){
        if(StringUtil.isNotEmpty(memberId)){
            TMember tMember = tMemberSrevice.getMemberInfo(memberId);
            return AjaxResult.success("获取会员信息成功",tMember);
        }else{
            return AjaxResult.success("ID错误");
        }
    }

    @ApiOperation("查询会员用户子女信息")
    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/getFavourite",method = RequestMethod.GET)
    public AjaxResult getFavourite(@RequestParam(value = "id") String memberId){
        if(StringUtil.isNotEmpty(memberId)){
            List<TMember> tMemberList = tMemberSrevice.getFavourite(memberId);
            return AjaxResult.success("获取会员信息成功",tMemberList);
        }else{
            return AjaxResult.success("ID错误");
        }
    }

    @ApiOperation("会员子女信息修改")
    @RequiresPermissions("wx:member:orders")
    @RequestMapping(value="/upMemberBaby",method = RequestMethod.GET)
    public AjaxResult upMemberBabyInfo(@RequestParam(value = "id") String memberBabyId,
                                           @RequestParam(value = "realName",required = false) String realName,
                                           @RequestParam(value = "sex",required = false)Integer sex,
                                           @RequestParam(value = "birthday",required = false)String birthday){
      if(StringUtil.isNotEmpty(memberBabyId)){
        int number = tMemberSrevice.upMemberBabyInfo(memberBabyId,realName,sex,birthday);
        return AjaxResult.success("修改会员子女信息成功",number);
      }else{
        return AjaxResult.success("ID错误");
      }
    }




//    @RequiresPermissions("wx:member:info")
//    @RequestMapping(value = "/info",method = RequestMethod.GET)
//    public AjaxResult getMemberInfo(@PathVariable("id") String memberId){
//        //
//        return null;
//    }


//    @RequiresPermissions("wx:member:orders")
//    @RequestMapping(value="/orders",method = RequestMethod.GET)
//    public AjaxResult getOrderInfo(@PathVariable("id") String memberId){
//        return null;
//    }
//
//    @RequiresPermissions("wx:member:favourite")
//    @RequestMapping(value="/favourite")
//    public AjaxResult getFavourite(){
//        return null;
//    }


//    public AjaxResult autoLogin(){
//        return null;
//    }

}
