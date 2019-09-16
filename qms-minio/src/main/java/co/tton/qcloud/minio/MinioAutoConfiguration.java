package co.tton.qcloud.minio;

import lombok.AllArgsConstructor;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-13 17:04
 */

@AllArgsConstructor
//@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    private final MinioProperties properties;


}
