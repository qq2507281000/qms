package co.tton.qcloud.system.service.impl;

import java.util.List;

import co.tton.qcloud.common.core.text.Convert;
import co.tton.qcloud.common.utils.DateUtils;
import co.tton.qcloud.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.tton.qcloud.system.mapper.TShopCoursesImagesMapper;
import co.tton.qcloud.system.domain.TShopCoursesImages;
import co.tton.qcloud.system.service.ITShopCoursesImagesService;

/**
 * 课程图片 建议使用minioService业务层处理
 * 
 * @author qcloud
 * @date 2019-09-05
 */
@Service
public class TShopCoursesImagesServiceImpl implements ITShopCoursesImagesService 
{
    @Autowired
    private TShopCoursesImagesMapper tShopCoursesImagesMapper;

    /**
     * 查询课程图片 建议使用minio
     * 
     * @param id 课程图片 建议使用minioID
     * @return 课程图片 建议使用minio
     */
    @Override
    public TShopCoursesImages selectTShopCoursesImagesById(String id)
    {
        return tShopCoursesImagesMapper.selectTShopCoursesImagesById(id);
    }

    /**
     * 查询课程图片 建议使用minio列表
     * 
     * @param tShopCoursesImages 课程图片 建议使用minio
     * @return 课程图片 建议使用minio
     */
    @Override
    public List<TShopCoursesImages> selectTShopCoursesImagesList(TShopCoursesImages tShopCoursesImages)
    {
        return tShopCoursesImagesMapper.selectTShopCoursesImagesList(tShopCoursesImages);
    }

    /**
     * 新增课程图片 建议使用minio
     * 
     * @param tShopCoursesImages 课程图片 建议使用minio
     * @return 结果
     */
    @Override
    public int insertTShopCoursesImages(TShopCoursesImages tShopCoursesImages)
    {
        tShopCoursesImages.setId(StringUtils.genericId());
        tShopCoursesImages.setCreateTime(DateUtils.getNowDate());
        return tShopCoursesImagesMapper.insertTShopCoursesImages(tShopCoursesImages);
    }

    /**
     * 修改课程图片 建议使用minio
     * 
     * @param tShopCoursesImages 课程图片 建议使用minio
     * @return 结果
     */
    @Override
    public int updateTShopCoursesImages(TShopCoursesImages tShopCoursesImages)
    {
        tShopCoursesImages.setUpdateTime(DateUtils.getNowDate());
        return tShopCoursesImagesMapper.updateTShopCoursesImages(tShopCoursesImages);
    }

    /**
     * 删除课程图片 建议使用minio对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTShopCoursesImagesByIds(String ids)
    {
        return tShopCoursesImagesMapper.deleteTShopCoursesImagesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除课程图片 建议使用minio信息
     * 
     * @param id 课程图片 建议使用minioID
     * @return 结果
     */
    public int deleteTShopCoursesImagesById(String id)
    {
        return tShopCoursesImagesMapper.deleteTShopCoursesImagesById(id);
    }
}
