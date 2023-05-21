package com.tqq.csmall.passport.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        /*临时使用，密码不加密*/
        /*return NoOpPasswordEncoder.getInstance();*/
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] urls = {
                "/doc.html","/**/*.css","/**/*.js","/swagger-resources",
                "/v2/api-docs"
        };
        /*super.configure(http);*/
        /*开始对请求进行授权*/
        /*匹配某些请求*/
        /*许可，不需要通过认证就可以访问*/
        /*任何请求*/
        /*已经完成认证的放行*/
        /*遵守先来先到的匹配规则*/
        http.authorizeRequests()
                .mvcMatchers(urls)
                .permitAll()
                .anyRequest()
                .authenticated();
        http.formLogin();
    }
}
