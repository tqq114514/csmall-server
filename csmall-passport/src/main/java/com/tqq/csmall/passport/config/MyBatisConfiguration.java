package com.tqq.csmall.passport.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@MapperScan("com.tqq.csmall.passport.mapper")
public class MyBatisConfiguration {

    public MyBatisConfiguration() {
        log.debug("创建配置类对象：MyBatisConfiguration");
    }

}
