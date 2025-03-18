package com.school.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ascrm
 * @Date: 2025/3/18
 */
@ConfigurationProperties(prefix = "task.pool")
@Component
@Data
public class ExecutorProperty {

    int coreSize;
    int maxSize;
    long keepAlive;
    TimeUnit fixedUnit = TimeUnit.MILLISECONDS;
    int queueCapacity;
}
