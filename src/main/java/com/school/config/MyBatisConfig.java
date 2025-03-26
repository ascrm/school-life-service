package com.school.config;

import com.school.interceptor.SqlCostInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    
    @Bean
    public SqlCostInterceptor sqlCostInterceptor() {
        return new SqlCostInterceptor();
    }
} 