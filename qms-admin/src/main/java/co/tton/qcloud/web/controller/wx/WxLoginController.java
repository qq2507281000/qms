package co.tton.qcloud.web.controller.wx;


import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.service.ITMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-09 13:38
 */


@Slf4j
@RestController
@RequestMapping("/api/v1.0/login")
@Api(value = "小程序获取Token和会员信息",tags="小程序获取Token和会员信息")
public class WxLoginController extends BaseController {
    @Autowired
    ITMemberService itMemberService;
    /**
     * <pre>
     * 获取用户信息和token接口
     * </pre>
     */
    @GetMapping("/user/loginInfo")
    @ApiOperation("返回个人信息和token")
//    @RequiresPermissions("wx:member:openId")
    public AjaxResult<List> loginInfo(@RequestParam(value = "openId",required = false) String openId) {
        if(StringUtils.isNotEmpty(openId)){
            TMember tMember = itMemberService.loginInfo(openId);
            List list = new ArrayList();
            list.add(tMember);
            if(StringUtils.isNull(tMember)){
                //如果查询不到就插入
                TMember tMemberOne = new TMember();
                tMemberOne.setId(StringUtils.genericId());
                tMemberOne.setOpenId(openId);
                int number = itMemberService.insertloginInfo(tMemberOne);
                list.add(number);
                return AjaxResult.success("新增用户插入成功。",list);
            }
            return AjaxResult.success("获取信息成功。",list);
          }else{
            return AjaxResult.error("openId错误。");
          }
      }
}
