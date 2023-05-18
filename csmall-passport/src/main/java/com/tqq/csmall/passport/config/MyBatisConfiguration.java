package com.tqq.csmall.passport.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.tqq.csmall.passport.mapper")
public class MyBatisConfiguration {
}
