package co.tton.qcloud.web.controller.wx;

import co.tton.qcloud.common.constant.Constants;
import co.tton.qcloud.common.core.controller.BaseController;
import co.tton.qcloud.common.core.domain.AjaxResult;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TFeedback;
import co.tton.qcloud.system.wxservice.IFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
  @RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
  @ApiOperation("新增反馈信息")
  public AjaxResult saveFeedback(@RequestBody TFeedback tFeedback) {
    if (StringUtils.isNotNull(tFeedback)) {
      tFeedback.setId(StringUtils.genericId());
      tFeedback.setCreateBy(tFeedback.getMemberId());
      tFeedback.setCreateTime(new Date());
      tFeedback.setFlag(Constants.DATA_NORMAL);
      int number = iFeedbackService.saveFeedback(tFeedback);//插入反馈信息
      if (number == 1) {
        return AjaxResult.success("新增反馈信息成功", number);
      } else {
        return AjaxResult.error("新增反馈信息失败");
      }
    } else {
      return AjaxResult.error("参数错误");
    }
  }
}
