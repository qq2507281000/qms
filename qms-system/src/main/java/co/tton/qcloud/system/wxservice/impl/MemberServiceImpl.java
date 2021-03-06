package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.domain.TMemberBaby;
import co.tton.qcloud.system.domain.TMemberModel;
import co.tton.qcloud.system.mapper.TMemberBabyMapper;
import co.tton.qcloud.system.mapper.TMemberMapper;
import co.tton.qcloud.system.wxservice.ITMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements ITMemberService {

  @Resource
  TMemberMapper tMemberMapper;
  @Resource
  TMemberBabyMapper tMemberBabyMapper;

  /**
   * 查询会员详情
   *
   * @param
   * @return 会员详情对象
   */
  @Override
  public TMemberModel getMemberInfo(String memberId) {
    return tMemberMapper.getMemberInfo(memberId);
  }

  /**
   * 查询会员详情
   *
   * @param
   * @return 查询会员用户子女信息
   */
  @Override
  public TMember getFavourite(String memberId) {
    return tMemberMapper.getFavourite(memberId);
  }

  /**
   * 会员子女信息修改
   *
   * @param
   * @return 结果
   */
  @Override
  public int upMemberBabyInfo(TMemberBaby tMemberBaby) {
    return tMemberBabyMapper.upMemberBabyInfo(tMemberBaby);
  }

  /**
   * 根据openId查询会员信息
   *
   * @param openId
   * @return 结果
   */
  @Override
  public TMember getMemberByOpenId(String openId) {
    return tMemberMapper.getMemberByOpenId(openId);
  }

    /**
     * 保存会员信息
     *
     * @param tMember
     * @return 结果
     */
    @Override
    public int saveMember(TMember tMember) {
        return tMemberMapper.saveMember(tMember);
    }

}
