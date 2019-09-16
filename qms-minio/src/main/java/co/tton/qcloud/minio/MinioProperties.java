package co.tton.qcloud.minio;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-13 16:06
 */

@Data
//@Configuration
//@ConfigurationProperties(prefix = "minio")
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

}
