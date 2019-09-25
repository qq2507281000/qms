package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import co.tton.qcloud.system.domain.TFeedback;
import co.tton.qcloud.system.mapper.TFeedbackMapper;
import co.tton.qcloud.system.wxservice.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements IFeedbackService {

    @Autowired
    private TFeedbackMapper tFeedbackMapper;

    @Override
    public void saveFeedback(TFeedback tFeedback) {
        tFeedback.setId(StringUtils.genericId());
        tFeedback.setCreateTime(DateUtils.getNowDate());
        tFeedbackMapper.saveFeedback(tFeedback);
    }
}
