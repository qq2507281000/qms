package co.tton.qcloud.system.service.impl;

import java.util.List;
import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TMemberBabyMapper;
import co.tton.qcloud.system.domain.TMemberBaby;
import co.tton.qcloud.system.service.ITMemberBabyService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TMemberBabyServiceImpl implements ITMemberBabyService 
{
    @Autowired
    private TMemberBabyMapper tMemberBabyMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TMemberBaby selectTMemberBabyById(String id)
    {
        return tMemberBabyMapper.selectTMemberBabyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tMemberBaby 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TMemberBaby> selectTMemberBabyList(TMemberBaby tMemberBaby)
    {
        return tMemberBabyMapper.selectTMemberBabyList(tMemberBaby);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tMemberBaby 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTMemberBaby(TMemberBaby tMemberBaby)
    {
        tMemberBaby.setCreateTime(DateUtils.getNowDate());
        return tMemberBabyMapper.insertTMemberBaby(tMemberBaby);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tMemberBaby 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTMemberBaby(TMemberBaby tMemberBaby)
    {
        tMemberBaby.setUpdateTime(DateUtils.getNowDate());
        return tMemberBabyMapper.updateTMemberBaby(tMemberBaby);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTMemberBabyByIds(String ids)
    {
        return tMemberBabyMapper.deleteTMemberBabyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTMemberBabyById(String id)
    {
        return tMemberBabyMapper.deleteTMemberBabyById(id);
    }
}
