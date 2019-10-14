package co.tton.qcloud.system.wxservice.impl;


import co.tton.qcloud.system.domain.TOrderUseEvaluation;
import co.tton.qcloud.system.mapper.TOrderUseEvaluationMapper;
import co.tton.qcloud.system.wxservice.ITOrderUseEvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ITOrderUseEvaluationServiceImpl implements ITOrderUseEvaluationService {
    @Resource
    TOrderUseEvaluationMapper tOrderUseEvaluationMapper;
    /**
     * 订单评价Service接口
     *
     * @author
     * @date
     */
    @Override
    public int insertOrderUseEvaluation(TOrderUseEvaluation tOrder) {
      return tOrderUseEvaluationMapper.insertOrderUseEvaluation(tOrder);
    }
}
