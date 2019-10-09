package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.system.domain.TFeedback;
import co.tton.qcloud.system.wxservice.IFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-18 19:17
 */

@RestController
@RequestMapping("/api/v1.0/feedback")
@Api(tags = "小程序反馈信息")
public class FeedbackController extends BaseController {

    @Autowired
    private IFeedbackService iFeedbackService;

//    @RequiresPermissions("wx:feedback")
    @RequestMapping(value = "/saveFeedback",method = RequestMethod.POST)
    @ApiOperation("新增反馈信息")
    public AjaxResult saveFeedback(TFeedback tFeedback){
        iFeedbackService.saveFeedback(tFeedback);
        return AjaxResult.success("新增反馈信息成功");
    }

}
