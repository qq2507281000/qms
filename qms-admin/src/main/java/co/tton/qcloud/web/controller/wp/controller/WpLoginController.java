package co.tton.qcloud.web.controller.wp.controller;

import co.tton.qcloud.common.config.Global;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.SysUser;
import co.tton.qcloud.system.model.ShopCenterModel;
import co.tton.qcloud.system.model.ShopCertModel;
import co.tton.qcloud.system.model.ShopOrderModel;
import co.tton.qcloud.system.service.ISysUserService;
import co.tton.qcloud.system.service.ITOrderService;
import co.tton.qcloud.system.service.ITShopService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-22 16:22
 */
@AllArgsConstructor
@Controller
@RequestMapping("/wp")
public class WpLoginController extends BaseController {

    private final WxMpService wxService;

    private final String prefix = "wp/";

    private ISysUserService userService;

    private ITShopService shopService;

    private ITOrderService orderService;

    @RequestMapping(value = "/shop/journey",method = RequestMethod.GET)
    public void redirectUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String appId = Global.getConfig("qcloud.active-wp-appId");
        String redirectUri = HtmlUtils.htmlEscape(Global.getConfig("qcloud.auth-url"));
        String state = StringUtils.randomCode();
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+redirectUri+"&response_type=code&scope=snsapi_userinfo&state="+state+"#wechat_redirect";
        response.sendRedirect(url);
    }

    @RequestMapping(value = "/shop/cert",method = RequestMethod.GET)
    public String shopAdminCert(@RequestParam("code")String code, ModelMap map,HttpServletResponse response){
        try{
            String appId = Global.getConfig("qcloud.active-wp-appId");
            logger.info("公众号appId-->"+appId);
            if(!wxService.switchover(appId)){
                return redirect("/wp/err");
            }
            else{
                WxMpOAuth2AccessToken accessToken = wxService.oauth2getAccessToken(code);
                WxMpUser user = wxService.oauth2getUserInfo(accessToken, null);
                map.put("user", user);
                String openId = user.getOpenId();
                //去数据库查询OpenId对应的Shop是否已经绑定
                SysUser sysUser = userService.selectUserByOpenId(openId);
//                if(user == null){
                if(sysUser == null){
                    return prefix + "shop-cert";
                }
                else{
                    getSession().setAttribute("sysUser",sysUser);
                    map.put("sysUser",sysUser);
                    return redirect("/wp/shop/center");
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            return redirect("/wp/err");
        }
    }

    @RequestMapping(value="/error",method = RequestMethod.GET)
    public String errorPage() {
        return prefix + "error";
    }

    /***
     * 商家认证接口
     * @param model
     * @return
     */
    @RequestMapping(value = "/shop/cert",method = RequestMethod.POST)
    public AjaxResult saveShopAdminCert(ShopCertModel model){
        try{
            if(model == null){
                return error("参数错误，对象不允许为空。");
            } else {
                String openId = model.getOpenId();
                String mobile = model.getMobile();

                SysUser user = userService.selectUserByPhoneNumber(mobile);
                if(user == null){
                    return error("未能找到用户对象信息。");
                } else {
                    user.setOpenId(openId);
                    user.setUpdateTime(DateUtils.getNowDate());
                    int result = userService.updateUser(user);
                    if (result == 1) {
                        return AjaxResult.success("商家认证成功。", user.getBusinessId());
                    }else{
                        return error("商家认证失败。");
                    }
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("获取商机订单信息时发生异常。");
            return error("获取商机订单信息时发生异常。");
        }
    }

    /***
     * 商家信息接口
     * @param model
     * @return
     */
    @RequestMapping(value = "/shop/center",method = RequestMethod.GET)
    public String selectShopCenter(ModelMap model){
        try{
            String id = "a34e61e8d282875c98e383af65e2c732";
            ShopCenterModel center = shopService.selectWPShopCenterById(id);
            model.put("center",center);
            return prefix + "shop-center";
        }
        catch(Exception ex){
            ex.printStackTrace();
            return redirect("/wp/err");
        }
    }


    /**
     *微信公众号商户订单列表
     * @param shopId
     * @param type
     * @return
     */
    @RequestMapping(value = "/shop/order",method = RequestMethod.POST)
    @PostMapping("/shop/order")
    @ResponseBody
    public AjaxResult selectShopOrder(String shopId,String type){
        try{
            if(shopId == null){
                return error("参数错误，对象不允许为空。");
            } else {
                List<ShopOrderModel> order = orderService.selectWPOrderByShopId(shopId,type);
                return AjaxResult.success(order);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            logger.error("保存商家认证信息时发生异常。");
            return error("保存商家认证信息时发生异常。");
        }
    }

    @GetMapping("/err")
    public String error(ModelMap map){
        return prefix + "shop-error";
    }




}
