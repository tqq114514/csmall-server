package com.tqq.csmall.passport.config;

import com.alibaba.fastjson.JSON;
import com.tqq.csmall.passport.web.JsonResult;
import com.tqq.csmall.passport.web.ServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启全局基于方法的安全检查
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        /*临时使用，密码不加密*/
        /*return NoOpPasswordEncoder.getInstance();*/
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*处理未通过认证时访问受保护资源时拒绝访问*/
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                String message ="当前未登录，不允许访问该资源！";
                log.warn(message);
                JsonResult jsonResult = JsonResult.fail(ServiceCode.ERR_UNAUTHORIZED, message);
                log.debug(String.valueOf(jsonResult));
                String jsonString = JSON.toJSONString(jsonResult);
                httpServletResponse.setContentType("application/json;charset=utf-8");

                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.println(jsonString);
                printWriter.close();
            }
        });
        /*解决post请求不可用，关闭防止伪造的跨域攻击的防御机制*/
        http.csrf().disable();
        String[] urls = {
                "/doc.html","/**/*.css","/**/*.js","/swagger-resources",
                "/v2/api-docs","/admin/login"
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
        //http.formLogin();
    }

}
