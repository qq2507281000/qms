package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TFeedback;

public interface IFeedbackService {

    /**
     * 存储反馈
     *
     * @param tFeedback
     * @return 结果
     */
    int saveFeedback(TFeedback tFeedback);
}
