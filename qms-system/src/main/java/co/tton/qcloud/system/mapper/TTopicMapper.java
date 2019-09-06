package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TTopic;
import java.util.List;

/**
 * 专题信息Mapper接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface TTopicMapper 
{
    /**
     * 查询专题信息
     * 
     * @param id 专题信息ID
     * @return 专题信息
     */
    public TTopic selectTTopicById(String id);

    /**
     * 查询专题信息列表
     * 
     * @param tTopic 专题信息
     * @return 专题信息集合
     */
    public List<TTopic> selectTTopicList(TTopic tTopic);

    /**
     * 新增专题信息
     * 
     * @param tTopic 专题信息
     * @return 结果
     */
    public int insertTTopic(TTopic tTopic);

    /**
     * 修改专题信息
     * 
     * @param tTopic 专题信息
     * @return 结果
     */
    public int updateTTopic(TTopic tTopic);

    /**
     * 删除专题信息
     * 
     * @param id 专题信息ID
     * @return 结果
     */
    public int deleteTTopicById(String id);

    /**
     * 批量删除专题信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTTopicByIds(String[] ids);
}
