package co.tton.qcloud.web.controller.wx;


import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.service.ITMemberService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String path;
    @Autowired
    ITMemberService itMemberService;

    /**
     * <pre>
     * 获取用户信息和token接口
     * </pre>
     */
    @GetMapping("/info")
    @ApiOperation("返回个人信息和token")
//    @RequiresPermissions("wx:member:openId")
    public AjaxResult<List> loginInfo(@RequestParam(value = "openId") String openId,
                                      @RequestParam(value = "mobile", required = false) String mobile,
                                      @RequestParam(value = "realName", required = false) String realName,
                                      @RequestParam(value = "img", required = false) String img
    ) {
        if (StringUtils.isNotEmpty(openId)) {
            TMember tMember = itMemberService.loginInfo(openId);
            if (StringUtils.isNull(tMember)) {
                //如果查询不到就插入
                TMember tMemberOne = new TMember();
                tMemberOne.setId(StringUtils.genericId());
                tMemberOne.setOpenId(openId);
                tMemberOne.setMobile(mobile);
                tMemberOne.setWxName(realName);
                tMemberOne.setImg(img);
                tMemberOne.setCreateTime(DateUtils.getNowDate());
                int number = itMemberService.insertloginInfo(tMemberOne);
                if (number == 1) {
                    return AjaxResult.success("新增用户插入成功。", number);
                }else{
                    return AjaxResult.success("新增用户插入失败。", number);
                }
            }
            return AjaxResult.success("获取信息成功。", tMember);
        } else {
            return AjaxResult.error("openId错误。");
        }
    }

    //    @RequiresPermissions("wx:code)
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @ApiOperation("获取code")
    public AjaxResult<Map> outSideCall(@RequestParam("code") String code) throws Exception {
        if (StringUtils.isNotEmpty(code)) {
            InputStream is = null;
            String body = null;
            StringBuilder res = new StringBuilder();
            Map returnMap = new HashMap<>();
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx979a25003d5b4c1b&secret=13da5ae620cba1a98e1f81e8241946f5&js_code=" + code + "&grant_type=authorization_code";
            HttpGet get = new HttpGet(url);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            try {
                response = client.execute(get);
                RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).build();
                get.setConfig(config);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    is = entity.getContent();
                    //转换为字节输入流
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));

                    while ((body = br.readLine()) != null) {
                        res.append(body);
                    }
                }
                returnMap = (Map) JSON.parse(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(StringUtils.isNull(returnMap)){
                return AjaxResult.error("失败",returnMap);
            }else{
                return AjaxResult.success("成功",returnMap);
            }
        } else {
            return AjaxResult.error("报错：code为空。");
        }
    }

//    public AjaxResult<TMember> wxLogin(@RequestParam(value = "openId") String openId,
//                                       @RequestParam(value = "mobile",required = false) String mobile,
//                                       @RequestParam(value = "realName",required = false) String realName,
//                                       @RequestParam(value = "avatar",required = false) String avatar){
//        if(StringUtils.isEmpty(openId)){
//            return AjaxResult.error("微信端OpenId不允许为空。");
//        }
//        else{
//            TMember member = itMemberService.loginInfo(openId);
//
//            if(member == null){
//                //如果OpenId为空，则向数据库中插入数据。
//            }
//            else{
//
//            }
//        }
//    }


}
