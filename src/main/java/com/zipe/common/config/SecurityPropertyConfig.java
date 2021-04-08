package com.zipe.common.config;

import com.zipe.enums.AuthoritzedTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Gary Tsai
 * @created : @Date 2021/4/8 下午 03:10
 **/
@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityPropertyConfig {
    private String enabled;
    private String allowUri;
    private String loginUri;
    private String loginFormUsernameFieldName = "username";
    private String loginFormPasswordFieldName = "password";
    private String defaultMainPageUri;
    private AuthoritzedTypeEnum authoritzedType;
    private String recordUserLogonEnabled = "false";
}
