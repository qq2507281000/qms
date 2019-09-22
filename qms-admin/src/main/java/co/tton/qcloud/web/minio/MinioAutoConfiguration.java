package co.tton.qcloud.web.minio;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-22 17:41
 */

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    private final MinioProperties properties;

    @Bean
    @ConditionalOnMissingBean(MinioTemplate.class)
    @ConditionalOnProperty(name = "minio.url")
    MinioTemplate template() {
        return new MinioTemplate(
                properties.getUrl(),
                properties.getAccessKey(),
                properties.getSecretKey()
        );
    }

}
