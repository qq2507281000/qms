package co.tton.qcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 * 
 * @author Qcloud
 */

@SpringBootApplication(
        exclude = { DataSourceAutoConfiguration.class },
        scanBasePackages = {"co.tton.qcloud.*"})
public class QcloudApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(QcloudApplication.class, args);
    }
}