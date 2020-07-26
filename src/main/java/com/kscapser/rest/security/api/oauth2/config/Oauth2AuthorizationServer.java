package com.kscapser.rest.security.api.oauth2.config;

import com.kscapser.rest.security.api.oauth2.common.properties.Oauth2Config;
import com.kscapser.rest.security.api.oauth2.common.utility.JwtTokenUtility;
import com.kscapser.rest.security.api.oauth2.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@Slf4j
public class Oauth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Oauth2Config oauth2Config;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        log.info("AuthorizationServerSecurityConfigurer: Step 1 - checkTokenAccess");
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("ClientDetailsServiceConfigurer: Step 2");
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.listClientDetails().stream().forEach(clientDetails -> log.info("ClientId : {} ",clientDetails.getClientId()));
        // client info store in inMemory
//        clients.inMemory()
//                .withClient("client")
//                .secret(encoder.encode("secret"))
//                .authorizedGrantTypes("authorization_code", "refresh_token", "password", "implicit")
//                .scopes("all")
//                .refreshTokenValiditySeconds(10)
//                .accessTokenValiditySeconds(10);
        clients.jdbc(dataSource).passwordEncoder(encoder).build();

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        log.info("AuthorizationServerEndpointsConfigurer: Step 3");
        endpoints
                .authorizationCodeServices(authorizationCodeServices())
                .tokenStore(tokenStore())
                .tokenEnhancer(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager);
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtTokenUtility(accountRepository);
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource(oauth2Config.getJksConfigFileName()), oauth2Config.getKeyPassword().toCharArray()).getKeyPair(oauth2Config.getJksKeyAlias()));
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        log.info("DefaultTokenServices tokenServices: Step 4");
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
