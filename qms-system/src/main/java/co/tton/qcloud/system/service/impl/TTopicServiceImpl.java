package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TTopicMapper;
import co.tton.qcloud.system.domain.TTopic;
import co.tton.qcloud.system.service.ITTopicService;

/**
 * 专题信息Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TTopicServiceImpl implements ITTopicService 
{
    @Autowired
    private TTopicMapper tTopicMapper;

    /**
     * 查询专题信息
     * 
     * @param id 专题信息ID
     * @return 专题信息
     */
    @Override
    public TTopic selectTTopicById(String id)
    {
        return tTopicMapper.selectTTopicById(id);
    }

    /**
     * 查询专题信息列表
     * 
     * @param tTopic 专题信息
     * @return 专题信息
     */
    @Override
    public List<TTopic> selectTTopicList(TTopic tTopic)
    {
        return tTopicMapper.selectTTopicList(tTopic);
    }

    /**
     * 新增专题信息
     * 
     * @param tTopic 专题信息
     * @return 结果
     */
    @Override
    public int insertTTopic(TTopic tTopic)
    {
        tTopic.setCreateTime(DateUtils.getNowDate());
        return tTopicMapper.insertTTopic(tTopic);
    }

    /**
     * 修改专题信息
     * 
     * @param tTopic 专题信息
     * @return 结果
     */
    @Override
    public int updateTTopic(TTopic tTopic)
    {
        tTopic.setUpdateTime(DateUtils.getNowDate());
        return tTopicMapper.updateTTopic(tTopic);
    }

    /**
     * 删除专题信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTTopicByIds(String ids)
    {
        return tTopicMapper.deleteTTopicByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除专题信息信息
     * 
     * @param id 专题信息ID
     * @return 结果
     */
    public int deleteTTopicById(String id)
    {
        return tTopicMapper.deleteTTopicById(id);
    }
}
