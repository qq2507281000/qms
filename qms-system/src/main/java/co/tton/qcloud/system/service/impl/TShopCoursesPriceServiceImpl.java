package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TShopCoursesPriceMapper;
import co.tton.qcloud.system.domain.TShopCoursesPrice;
import co.tton.qcloud.system.service.ITShopCoursesPriceService;

/**
 * 课程价格Service业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesPriceServiceImpl implements ITShopCoursesPriceService 
{
    @Autowired
    private TShopCoursesPriceMapper tShopCoursesPriceMapper;

    /**
     * 查询课程价格
     * 
     * @param id 课程价格ID
     * @return 课程价格
     */
    @Override
    public TShopCoursesPrice selectTShopCoursesPriceById(String id)
    {
        return tShopCoursesPriceMapper.selectTShopCoursesPriceById(id);
    }

    /**
     * 查询课程价格列表
     * 
     * @param tShopCoursesPrice 课程价格
     * @return 课程价格
     */
    @Override
    public List<TShopCoursesPrice> selectTShopCoursesPriceList(TShopCoursesPrice tShopCoursesPrice)
    {
        tShopCoursesPrice.setFlag(1);
        return tShopCoursesPriceMapper.selectTShopCoursesPriceList(tShopCoursesPrice);
    }

    /**
     * 新增课程价格
     * 
     * @param tShopCoursesPrice 课程价格
     * @return 结果
     */
    @Override
    public int insertTShopCoursesPrice(TShopCoursesPrice tShopCoursesPrice)
    {
        tShopCoursesPrice.setId(StringUtils.genericId());
        tShopCoursesPrice.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesPriceMapper.insertTShopCoursesPrice(tShopCoursesPrice);
    }

    /**
     * 修改课程价格
     * 
     * @param tShopCoursesPrice 课程价格
     * @return 结果
     */
    @Override
    public int updateTShopCoursesPrice(TShopCoursesPrice tShopCoursesPrice)
    {
        tShopCoursesPrice.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesPriceMapper.updateTShopCoursesPrice(tShopCoursesPrice);
    }

    /**
     * 删除课程价格对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTShopCoursesPriceByIds(String ids)
    {
        return tShopCoursesPriceMapper.deleteTShopCoursesPriceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程价格信息
     * 
     * @param id 课程价格ID
     * @return 结果
     */
    public int deleteTShopCoursesPriceById(String id)
    {
        return tShopCoursesPriceMapper.deleteTShopCoursesPriceById(id);
    }
}
