package co.tton.qcloud.minio.service;

import co.tton.qcloud.minio.vo.MinioItem;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-16 09:20
 */

@RequiredArgsConstructor
public class MinioTemplate implements InitializingBean {

    @Value("${minio.url}")
    private final String endpoint;

    @Value("${minio.access-key}")
    private final String accessKey;

    @Value("${minio.secret-key}")
    private final String secretKey;

    private MinioClient client;

    /***
     * 创建Bucket
     * @param bucketName
     */
    @SneakyThrows
    public void createBucket(String bucketName){
        if(!client.bucketExists(bucketName)){
            client.makeBucket(bucketName);
        }
    }

    /***
     * 获取所有Bucket
     * @return
     */
    @SneakyThrows
    public List<Bucket> getBuckets(){
        return client.listBuckets();
    }

    /***
     * 判断Bucket是否存在
     * @param bucketName
     * @return
     */
    @SneakyThrows
    public boolean isBucketExist(String bucketName){
        return client.bucketExists(bucketName);
    }

    /***
     * 获取单一Bucket
     * @param bucketName
     * @return
     */
    @SneakyThrows
    public Bucket getBucket(String bucketName){
        return getBuckets().stream().filter(d-> StringUtils.equals(d.name(),bucketName)).findFirst().orElse(null);
    }

    /***
     * 移除Bucket
     * @param bucketName
     */
    @SneakyThrows
    public void removeBucket(String bucketName){
        client.removeBucket(bucketName);
    }

    /**
     * 根据前缀获取Bucket名称
     * @param bucketName
     * @param prefix
     * @param recursive
     * @return
     */
    @SneakyThrows
    public List<MinioItem> getAllObjectsByPrefix(String bucketName,String prefix,boolean recursive){
        List<MinioItem> objectList = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = client
                .listObjects(bucketName, prefix, recursive);

        while (objectsIterator.iterator().hasNext()) {
            objectList.add(new MinioItem(objectsIterator.iterator().next().get()));
        }
        return objectList;
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return client.presignedGetObject(bucketName, objectName, expires);
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return client.getObject(bucketName, objectName);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        client.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        client.putObject(bucketName, objectName, stream, size, contextType);
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    public ObjectStat getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(bucketName, objectName);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(bucketName, objectName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(endpoint, "Minio url 为空");
        Assert.hasText(accessKey, "Minio accessKey为空");
        Assert.hasText(secretKey, "Minio secretKey为空");
        this.client = new MinioClient(endpoint, accessKey, secretKey);
    }

}
