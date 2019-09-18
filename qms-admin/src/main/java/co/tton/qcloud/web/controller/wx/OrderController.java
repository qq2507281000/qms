package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 18:49
 */

@RestController
@RequestMapping("/api/v1.0/order")
public class OrderController extends BaseController {

    @RequiresPermissions("wx:order:submit")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public AjaxResult submitOrder(){
        //TODO:参数未定义，提交数据需要有实体对象。
        return null;
    }

    @RequiresPermissions("wx:order:list")
    @RequestMapping(value="",method = RequestMethod.GET)
    public AjaxResult getOrderList(){
        return null;
    }

    @RequiresPermissions("wx:order:detail")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public AjaxResult getOrderDetail(@PathVariable("id")String orderId){
        return null;
    }

    @RequiresPermissions("wx:order:comment")
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public AjaxResult comment(){
        return null;
    }


}
