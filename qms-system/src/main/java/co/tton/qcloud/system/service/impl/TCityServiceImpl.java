package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TCityMapper;
import co.tton.qcloud.system.domain.TCity;
import co.tton.qcloud.system.service.ITCityService;

/**
 * cityService业务层处理
 * 
 * @author qcloud
 * @date 2019-10-17
 */
@Service
public class TCityServiceImpl implements ITCityService 
{
    @Autowired
    private TCityMapper tCityMapper;

    /**
     * 查询city
     * 
     * @param id cityID
     * @return city
     */
    @Override
    public TCity selectTCityById(String id)
    {
        return tCityMapper.selectTCityById(id);
    }

    /**
     * 查询city列表
     * 
     * @param tCity city
     * @return city
     */
    @Override
    public List<TCity> selectTCityList(TCity tCity)
    {
        return tCityMapper.selectTCityList(tCity);
    }

    /**
     * 新增city
     * 
     * @param tCity city
     * @return 结果
     */
    @Override
    public int insertTCity(TCity tCity)
    {
        tCity.setCreateTime(DateUtils.getNowDate());
        return tCityMapper.insertTCity(tCity);
    }

    /**
     * 修改city
     * 
     * @param tCity city
     * @return 结果
     */
    @Override
    public int updateTCity(TCity tCity)
    {
        tCity.setUpdateTime(DateUtils.getNowDate());
        return tCityMapper.updateTCity(tCity);
    }

    /**
     * 删除city对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTCityByIds(String ids)
    {
        return tCityMapper.deleteTCityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除city信息
     * 
     * @param id cityID
     * @return 结果
     */
    public int deleteTCityById(String id)
    {
        return tCityMapper.deleteTCityById(id);
    }
}
