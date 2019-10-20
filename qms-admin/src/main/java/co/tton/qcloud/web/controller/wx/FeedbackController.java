package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TFeedback;
import co.tton.qcloud.system.wxservice.IFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public AjaxResult saveFeedback(@RequestParam("memberid") String memberId, @RequestParam("content") String content){
        if(StringUtils.isNotEmpty(memberId)){
            TFeedback tFeedback = new TFeedback();
            tFeedback.setMemberId(memberId);
            tFeedback.setContent(content);
            tFeedback.setId(StringUtils.genericId());
            tFeedback.setCreateBy(memberId);
            tFeedback.setCreateTime(new Date());
            tFeedback.setFlag(Constants.DATA_NORMAL);
            int number = iFeedbackService.saveFeedback(tFeedback);
            if(number == 1){
                return AjaxResult.success("新增反馈信息成功",number);
            }else{
                return AjaxResult.success("新增反馈信息失败",number);
            }
        }else{
            return AjaxResult.error("参数错误");
        }
    }

}
