package co.tton.qcloud.system.wxservice;

import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.domain.TMemberModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员信息Service接口
 *
 * @author qcloud
 * @date 2019-09-24
 */
public interface ITMemberService {

  /**
   * 查询会员详情
   *
   * @param
   * @return 会员详情对象
   */
  TMemberModel getMemberInfo(String memberId);

  /**
   * 查询会员用户子女信息
   *
   * @param
   * @return 查询会员用户子女信息
   */
  List<TMember> getFavourite(String memberId);

  /**
   * 会员子女信息修改
   *
   * @param
   * @return 结果
   */
  int upMemberBabyInfo(@Param("id") String memberBabyId, @Param("realName") String realName, @Param("sex") Integer sex, @Param("birthday") String birthday);

}
