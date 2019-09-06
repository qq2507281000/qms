package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TMemberBaby;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TMemberBabyMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public TMemberBaby selectTMemberBabyById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tMemberBaby 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TMemberBaby> selectTMemberBabyList(TMemberBaby tMemberBaby);

    /**
     * 新增【请填写功能名称】
     * 
     * @param tMemberBaby 【请填写功能名称】
     * @return 结果
     */
    public int insertTMemberBaby(TMemberBaby tMemberBaby);

    /**
     * 修改【请填写功能名称】
     * 
     * @param tMemberBaby 【请填写功能名称】
     * @return 结果
     */
    public int updateTMemberBaby(TMemberBaby tMemberBaby);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTMemberBabyById(String id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTMemberBabyByIds(String[] ids);
}
