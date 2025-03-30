package com.school.config;

import com.school.interceptor.SqlCostInterceptor;
import com.school.interceptor.SqlIndexInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ascrm
 * @Date: 2025/3/28
 */
@Configuration
@Slf4j
public class MybatisConfig {
    @Bean
    public SqlCostInterceptor sqlCostInterceptor() {
        log.info("SQL耗时拦截器已开启");
        return new SqlCostInterceptor();
    }

    @Bean
    public SqlIndexInterceptor sqlIndexInterceptor() {
        log.info("SQL索引拦截器已开启");
        return new SqlIndexInterceptor();
    }
}
