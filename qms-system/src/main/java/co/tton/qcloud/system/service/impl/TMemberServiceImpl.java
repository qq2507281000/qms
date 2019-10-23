package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TMemberMapper;
import co.tton.qcloud.system.domain.TMember;
import co.tton.qcloud.system.service.ITMemberService;

import javax.annotation.Resource;

/**
 * 会员基本信息Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TMemberServiceImpl implements ITMemberService 
{
    @Resource
    private TMemberMapper tMemberMapper;

    /**
     * 查询会员基本信息
     * 
     * @param id 会员基本信息ID
     * @return 会员基本信息
     */
    @Override
    public TMember selectTMemberById(String id)
    {
        return tMemberMapper.selectTMemberById(id);
    }

    /**
     * 查询会员基本信息列表
     * 
     * @param tMember 会员基本信息
     * @return 会员基本信息
     */
    @Override
    public List<TMember> selectTMemberList(TMember tMember)
    {
        return tMemberMapper.selectTMemberList(tMember);
    }

    /**
     * 新增会员基本信息
     * 
     * @param tMember 会员基本信息
     * @return 结果
     */
    @Override
    public int insertTMember(TMember tMember)
    {
        tMember.setCreateTime(DateUtils.getNowDate());
        return tMemberMapper.insertTMember(tMember);
    }

    /**
     * 修改会员基本信息
     * 
     * @param tMember 会员基本信息
     * @return 结果
     */
    @Override
    public int updateTMember(TMember tMember)
    {
        tMember.setUpdateTime(DateUtils.getNowDate());
        return tMemberMapper.updateTMember(tMember);
    }

    /**
     * 删除会员基本信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTMemberByIds(String ids)
    {
        return tMemberMapper.deleteTMemberByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除会员基本信息信息
     * 
     * @param id 会员基本信息ID
     * @return 结果
     */
    public int deleteTMemberById(String id)
    {
        return tMemberMapper.deleteTMemberById(id);
    }

    /**
     * 登录时会员基本信息查询
     *
     * @param openId 会员基本信息openId
     * @return
     */
    @Override
    public TMember loginInfo(String openId) {
        return tMemberMapper.loginInfo(openId);
    }

    /**
     * 新增会员用户
     *
     * @param tMember
     * @return 结果
     */
    @Override
    public int insertloginInfo(TMember tMember) {
        return tMemberMapper.insertloginInfo(tMember);
    }

    /**
     * 修改会员基本信息
     *
     * @param tMember 会员基本信息
     * @return 结果
     */
    @Override
    public int updateTMemberOpenId(TMember tMember) {
        return tMemberMapper.updateTMemberOpenId(tMember);
    }
}
