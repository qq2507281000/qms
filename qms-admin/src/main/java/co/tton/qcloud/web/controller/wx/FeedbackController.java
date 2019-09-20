package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 19:17
 */

@RequestMapping("/api/v1.0/feedback")
public class FeedbackController extends BaseController {

    @RequiresPermissions("wx:feedback")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public AjaxResult saveFeedback(){
        return null;
    }

}
