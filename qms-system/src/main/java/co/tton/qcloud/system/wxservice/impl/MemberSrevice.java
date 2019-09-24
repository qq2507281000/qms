package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.mapper.TMemberMapper;
import co.tton.qcloud.system.wxservice.ITMemberSrevice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberSrevice implements ITMemberSrevice {

  @Resource
  TMemberMapper tMemberMapper;

  /**
   * 查询会员详情
   *
   * @param
   * @return 会员详情对象
   */
  @Override
  public TMember getMemberInfo(String memberId) {
    return tMemberMapper.getMemberInfo(memberId);
  }

  /**
   * 查询会员详情
   *
   * @param
   * @return 查询会员用户子女信息
   */
  @Override
  public List<TMember> getOrderInfo(String memberId) {
    return tMemberMapper.getOrderInfo(memberId);
  }
}
