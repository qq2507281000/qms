package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TMember;
import java.util.List;

/**
 * 会员基本信息Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TMemberMapper 
{
    /**
     * 查询会员基本信息
     * 
     * @param id 会员基本信息ID
     * @return 会员基本信息
     */
    public TMember selectTMemberById(String id);

    /**
     * 查询会员基本信息列表
     * 
     * @param tMember 会员基本信息
     * @return 会员基本信息集合
     */
    public List<TMember> selectTMemberList(TMember tMember);

    /**
     * 新增会员基本信息
     * 
     * @param tMember 会员基本信息
     * @return 结果
     */
    public int insertTMember(TMember tMember);

    /**
     * 修改会员基本信息
     * 
     * @param tMember 会员基本信息
     * @return 结果
     */
    public int updateTMember(TMember tMember);

    /**
     * 删除会员基本信息
     * 
     * @param id 会员基本信息ID
     * @return 结果
     */
    public int deleteTMemberById(String id);

    /**
     * 批量删除会员基本信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTMemberByIds(String[] ids);

    /**
     * 查询会员详情
     *
     * @param
     * @return 会员详情对象
     */
    TMember getMemberInfo(String memberId);

    /**
     * 查询会员详情
     *
     * @param
     * @return 查询会员用户子女信息
     */
    List<TMember> getOrderInfo(String memberId);
}
