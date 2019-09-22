package co.tton.qcloud.web.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-22 17:41
 */
@Data
//@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * minio 服务地址 http://ip:port
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

}
