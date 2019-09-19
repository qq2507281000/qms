package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TShopCoursesTimeMapper;
import co.tton.qcloud.system.domain.TShopCoursesTime;
import co.tton.qcloud.system.service.ITShopCoursesTimeService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesTimeServiceImpl implements ITShopCoursesTimeService 
{
    @Autowired
    private TShopCoursesTimeMapper tShopCoursesTimeMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public TShopCoursesTime selectTShopCoursesTimeById(String id)
    {
        return tShopCoursesTimeMapper.selectTShopCoursesTimeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param tShopCoursesTime 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TShopCoursesTime> selectTShopCoursesTimeList(TShopCoursesTime tShopCoursesTime)
    {
        tShopCoursesTime.setFlag(1);
        return tShopCoursesTimeMapper.selectTShopCoursesTimeList(tShopCoursesTime);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param tShopCoursesTime 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTShopCoursesTime(TShopCoursesTime tShopCoursesTime)
    {
        tShopCoursesTime.setId(StringUtils.genericId());
        tShopCoursesTime.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesTimeMapper.insertTShopCoursesTime(tShopCoursesTime);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param tShopCoursesTime 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTShopCoursesTime(TShopCoursesTime tShopCoursesTime)
    {
        tShopCoursesTime.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesTimeMapper.updateTShopCoursesTime(tShopCoursesTime);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTShopCoursesTimeByIds(String ids)
    {
        return tShopCoursesTimeMapper.deleteTShopCoursesTimeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteTShopCoursesTimeById(String id)
    {
        return tShopCoursesTimeMapper.deleteTShopCoursesTimeById(id);
    }
}
