package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TFeedback;

public interface TFeedbackMapper {

    /**
     * 存储反馈
     *
     * @param tFeedback
     * @return 结果
     */
    int saveFeedback(TFeedback tFeedback);
}
