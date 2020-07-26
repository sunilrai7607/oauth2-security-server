package com.kscapser.rest.security.api.oauth2.config;

import com.kscapser.rest.security.api.oauth2.service.ICustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class Oauth2WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ICustomUserDetails customUserDetails;

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        log.info("Inside config authenticationManagerBean ");
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Inside config AuthenticationManagerBuilder ");
        auth.userDetailsService(customUserDetails).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Inside config HttpSecurity ");
        super.configure(http);
    }
}
