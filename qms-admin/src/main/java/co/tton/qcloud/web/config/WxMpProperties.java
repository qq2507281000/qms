package co.tton.qcloud.web.config;

import co.tton.qcloud.common.utils.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @program: qms
 * @description:
 * @author: Rain@TTON
 * @create: 2019-10-22 15:00
 */
@Data
@ConfigurationProperties(prefix = "wx.wp")
public class WxMpProperties {

    private List<Config> configs;

    @Data
    public static class Config {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}

