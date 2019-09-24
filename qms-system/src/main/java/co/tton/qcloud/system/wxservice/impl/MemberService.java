package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.mapper.TMemberBabyMapper;
import co.tton.qcloud.system.mapper.TMemberMapper;
import co.tton.qcloud.system.wxservice.ITMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberService implements ITMemberService {

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
  public List<TMember> getFavourite(String memberId) {
    return tMemberMapper.getFavourite(memberId);
  }

  /**
   * 会员子女信息修改
   *
   * @param
   * @return 结果
   */
  @Override
  public int upMemberBabyInfo(String memberBabyId, String realName, Integer sex, String birthday) {
//    Date date =null;
//    if(StringUtil.isNotEmpty(birthday)){
//      //生日类型转换
//      SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//      try {
//        date = sdf.parse(birthday);
//      } catch (ParseException e) {
//        e.printStackTrace();
//      }
//    }
    return tMemberBabyMapper.upMemberBabyInfo(memberBabyId,realName,sex,birthday);
  }
}
