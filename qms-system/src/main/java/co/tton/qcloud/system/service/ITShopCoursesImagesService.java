package co.tton.qcloud.system.service;

import co.tton.qcloud.system.domain.TShopCoursesImages;
import java.util.List;

/**
 * 课程图片 建议使用minioService接口
 * 
 * @author qcloud
 * @date 2019-09-05
 */
public interface ITShopCoursesImagesService 
{
    /**
     * 查询课程图片 建议使用minio
     * 
     * @param id 课程图片 建议使用minioID
     * @return 课程图片 建议使用minio
     */
    public TShopCoursesImages selectTShopCoursesImagesById(String id);

    /**
     * 查询课程图片 建议使用minio列表
     * 
     * @param tShopCoursesImages 课程图片 建议使用minio
     * @return 课程图片 建议使用minio集合
     */
    public List<TShopCoursesImages> selectTShopCoursesImagesList(TShopCoursesImages tShopCoursesImages);

    /**
     * 新增课程图片 建议使用minio
     * 
     * @param tShopCoursesImages 课程图片 建议使用minio
     * @return 结果
     */
    public int insertTShopCoursesImages(TShopCoursesImages tShopCoursesImages);

    /**
     * 修改课程图片 建议使用minio
     * 
     * @param tShopCoursesImages 课程图片 建议使用minio
     * @return 结果
     */
    public int updateTShopCoursesImages(TShopCoursesImages tShopCoursesImages);

    /**
     * 批量删除课程图片 建议使用minio
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTShopCoursesImagesByIds(String ids);

    /**
     * 删除课程图片 建议使用minio信息
     * 
     * @param id 课程图片 建议使用minioID
     * @return 结果
     */
    public int deleteTShopCoursesImagesById(String id);

    /***
     * 根据课程Id获取课程图片地址
     * @param courses_id
     * @return
     */
    public List<String> getCoursesImages(String courses_id);
}
