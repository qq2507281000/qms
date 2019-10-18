package co.tton.qcloud.system.mapper;

import co.tton.qcloud.system.domain.TCity;
import java.util.List;

/**
 * cityMapper接口
 * 
 * @author qcloud
 * @date 2019-10-17
 */
public interface TCityMapper 
{
    /**
     * 查询city
     * 
     * @param id cityID
     * @return city
     */
    public TCity selectTCityById(String id);

    /**
     * 查询city列表
     * 
     * @param tCity city
     * @return city集合
     */
    public List<TCity> selectTCityList(TCity tCity);

    /**
     * 新增city
     * 
     * @param tCity city
     * @return 结果
     */
    public int insertTCity(TCity tCity);

    /**
     * 修改city
     * 
     * @param tCity city
     * @return 结果
     */
    public int updateTCity(TCity tCity);

    /**
     * 删除city
     * 
     * @param id cityID
     * @return 结果
     */
    public int deleteTCityById(String id);

    /**
     * 批量删除city
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTCityByIds(String[] ids);
}
