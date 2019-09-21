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
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    private final MinioProperties properties;
    @Autowired lateinit var minioProperties: MinioProperties;

    @Bean
    @ConditionalOnMissingBean(MinioTemplate::class)
    @ConditionalOnProperty(name = arrayOf("minio.url"))
    open fun minioTemplate():MinioTemplate {
        return MinioTemplate(minioProperties.url,
            minioProperties.accessKey,
            minioProperties.secretKey)
    }
    MinioTemplate template() {
        return new MinioTemplate(this.properties.getUrl(), this.properties.getAccessKey(), this.properties.getSecretKey());
    }

    public MinioAutoConfiguration(MinioProperties properties) {
        this.properties = properties;
    }
}
