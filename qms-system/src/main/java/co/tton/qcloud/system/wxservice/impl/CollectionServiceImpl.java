package co.tton.qcloud.system.wxservice.impl;

import co.tton.qcloud.system.domain.TCollection;
import co.tton.qcloud.system.mapper.TCollectionMapper;
import co.tton.qcloud.system.wxservice.ITCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 收藏夹Service业务层处理
 *
 * @author qcloud
 * @date 2019-10-11
 */
@Service
public class CollectionServiceImpl implements ITCollectionService {
    @Resource
    private TCollectionMapper tCollectionMapper;

    /**
    * 添加收藏
    *
    * @param tCollection 需要添加的数据
    * @return 结果
    */
    @Override
    public int insertCollection(TCollection tCollection) {
      return tCollectionMapper.insertCollection(tCollection);
    }

    /**
     * 查询收藏
     *
     * @param tCollection 查询条件
     * @return 结果
     */
    @Override
    public TCollection getCollection(TCollection tCollection) {
        return tCollectionMapper.getCollection(tCollection);
    }

    /**
     * 取消收藏
     *
     * @param tCollection 取消条件
     * @return 结果
     */
    @Override
    public int deleteCollection(TCollection tCollection) {
        return tCollectionMapper.deleteCollection(tCollection);
    }
}
