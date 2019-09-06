package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TMember;
import java.util.List;

/**
 * 会员基本信息Service接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITMemberService 
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
     * 批量删除会员基本信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTMemberByIds(String ids);

    /**
     * 删除会员基本信息信息
     * 
     * @param id 会员基本信息ID
     * @return 结果
     */
    public int deleteTMemberById(String id);
}
