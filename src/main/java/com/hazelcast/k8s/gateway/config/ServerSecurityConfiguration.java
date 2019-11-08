package com.hazelcast.k8s.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ServerSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .headers().frameOptions().disable().and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().anonymous()
                .and()
                .oauth2ResourceServer()
                .jwt();
        // @formatter:on
    }
}
