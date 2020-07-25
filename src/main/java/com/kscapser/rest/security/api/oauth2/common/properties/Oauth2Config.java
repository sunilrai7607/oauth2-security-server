package com.kscapser.rest.security.api.oauth2.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("oauth2-config")
public class Oauth2Config {

    public Boolean userScopes;
    private String jksConfigFileName;
    private String keyPassword;
    private String jksKeyAlias;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
}
