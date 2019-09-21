package co.tton.qcloud.minio;

import co.tton.qcloud.minio.service.MinioTemplate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-09-13 17:04
 */

@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    private final MinioProperties properties;

    @Bean
    @ConditionalOnMissingBean({MinioTemplate.class})
    @ConditionalOnProperty(
            name = {"minio.url"}
    )
    MinioTemplate template() {
        return new MinioTemplate(this.properties.getUrl(), this.properties.getAccessKey(), this.properties.getSecretKey());
    }

    public MinioAutoConfiguration(MinioProperties properties) {
        this.properties = properties;
    }
}
