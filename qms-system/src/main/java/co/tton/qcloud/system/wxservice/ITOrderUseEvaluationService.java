package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TOrderUseEvaluation;

/**
 * 订单评价Service接口
 *
 * @author
 * @date 2019-10-12
 */
public interface ITOrderUseEvaluationService {

    /**
     * 新增评价
     *
     * @param
     * @return 结果
     */
    int insertOrderUseEvaluation(TOrderUseEvaluation tOrder);
}
