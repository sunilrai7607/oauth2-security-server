package com.kscapser.rest.security.api.oauth2.common.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
public class JwtTokenUtility extends JwtAccessTokenConverter {

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        log.info("Called JwtTokenUtility.encode {}",accessToken);
        return super.encode(accessToken, authentication);
    }

    @Override
    protected Map<String, Object> decode(String token) {
        log.info("Called JwtTokenUtility.decode {} ",token);
        return super.decode(token);
    }

}
