package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TMember;

/**
 * 会员信息Service接口
 *
 * @author qcloud
 * @date 2019-09-24
 */
public interface ITMemberSrevice  {

  /**
   * 查询会员详情
   *
   * @param
   * @return 会员详情对象
   */
  TMember getMemberInfo(String memberId);

}
