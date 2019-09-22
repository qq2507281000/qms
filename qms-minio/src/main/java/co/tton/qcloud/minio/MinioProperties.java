package co.tton.qcloud.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-13 16:06
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /***
     * 文件地址
     */
    private String url;

    /***
     * 授权账户
     */
    private String accessKey;

    /***
     * 授权秘钥
     */
    private String secretKey;

    /***
     * 文件上传根目录
     */
    private String bucketName;

    @java.lang.Override
    public java.lang.String toString() {
        return "MinioProperties{" +
            "url='" + url + '\'' +
            ", accessKey='" + accessKey + '\'' +
            ", secretKey='" + secretKey + '\'' +
            '}';
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getAccessKey() {
//        return accessKey;
//    }
//
//    public void setAccessKey(String accessKey) {
//        this.accessKey = accessKey;
//    }
//
//    public String getSecretKey() {
//        return secretKey;
//    }
//
//    public void setSecretKey(String secretKey) {
//        this.secretKey = secretKey;
//    }
//
//    /***
//     * 授权秘钥
//     */
//    private String secretKey;

}
