package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏夹Mapper接口
 *
 * @author qcloud
 * @date 2019-10-11
 */
public interface TCollectionMapper {

    /**
     * 添加收藏
     *
     * @param tCollection 需要添加的数据
     * @return 结果
     */
    int insertCollection(TCollection tCollection);

    /**
     * 查询收藏
     *
     * @param tCollection 查询条件
     * @return 结果
     */
    List<TCollection> getCollection(TCollection tCollection);

    /**
     * 取消收藏
     *
     * @param tCollection 取消条件
     * @return 结果
     */
    int deleteCollection(TCollection tCollection);

    /**
     * 判断是否收藏课程
     *
     * @param tCollection 查询条件
     * @return 结果
     */
    TCollection userCollection(TCollection tCollection);
}
