package com.travel.config;

import com.travel.common.properties.ExecutorProperty;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Author: ascrm
 * @Date: 2025/3/18
 */
@Configuration
public class ThreadPoolConfig {

    @Resource
    private ExecutorProperty executorProperty;

    @Bean
    public ExecutorService aiTaskExecutor() {
        return new ThreadPoolExecutor(
                executorProperty.getCoreSize(),
                executorProperty.getMaxSize(),
                executorProperty.getKeepAlive(),
                executorProperty.getFixedUnit(),
                new LinkedBlockingDeque<>(executorProperty.getQueueCapacity())
        );
    }
}
