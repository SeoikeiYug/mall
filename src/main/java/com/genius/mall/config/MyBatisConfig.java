package com.genius.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.genius.mall.mbg.mapper", "com.genius.mall.dao"})
public class MyBatisConfig {
}
