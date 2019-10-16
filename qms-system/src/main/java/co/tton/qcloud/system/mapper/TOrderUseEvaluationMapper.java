package co.tton.qcloud.system.mapper;


import co.tton.qcloud.system.domain.TOrderUseEvaluation;

import java.util.List;

/**
 * Mapper接口
 *
 * @author qcloud
 * @date 2019-10-12
 */
public interface TOrderUseEvaluationMapper {

  /**
   * 新增评价
   *
   * @param
   * @return 结果
   */
  int insertOrderUseEvaluation(TOrderUseEvaluation tOrder);

  /**
   * 获取课程评价
   *
   * @param  coursesId 查询
   * @return 结果
   */
  List<TOrderUseEvaluation> getCoursesCategory(String coursesId);
}
