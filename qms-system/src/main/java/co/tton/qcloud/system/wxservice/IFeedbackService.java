package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TFeedback;

public interface IFeedbackService {

    int saveFeedback(TFeedback tFeedback);
}
