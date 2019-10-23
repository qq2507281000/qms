package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TFeedback;
import co.tton.qcloud.system.mapper.TFeedbackMapper;
import co.tton.qcloud.system.wxservice.IFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    @Resource
    private TFeedbackMapper tFeedbackMapper;

    /**
     * 存储反馈
     *
     * @param tFeedback
     * @return 结果
     */
    @Override
    public int saveFeedback(TFeedback tFeedback) {
        return tFeedbackMapper.saveFeedback(tFeedback);
    }
}
