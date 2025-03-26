package com.school.handler;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnProperty(name = "spring.datasource.hikari.warm-up-enabled", havingValue = "true")
public class DataSourceWarmerHandler {

    @Resource
    private HikariDataSource dataSource;

    @Resource
    private ExecutorService executorService;

    @EventListener(ApplicationStartedEvent.class)
    public void warmUp() {
        new Thread(() -> {
            int poolSize = dataSource.getHikariConfigMXBean().getMinimumIdle();
            int batchSize = 2; // 每批预热的连接数
            
            log.info("异步开始分批预热数据库连接池，总连接数：{}", poolSize);
            try {
                // 分批预热
                for (int batch = 0; batch < poolSize; batch += batchSize) {
                    int currentBatchSize = Math.min(batchSize, poolSize - batch);
                    
                    // 预热当前批次
                    for (int i = 0; i < currentBatchSize; i++) {
                        executorService.submit(() -> {
                            try (Connection connection = dataSource.getConnection()) {
                                try (var stmt = connection.prepareStatement("SELECT 1")) {
                                    stmt.execute();
                                }
                                log.info("连接池预热 - 成功创建一个连接");
                            } catch (Exception e) {
                                log.error("连接池预热 - 创建连接失败", e);
                            }
                        });
                    }
                    
                    // 等待当前批次完成
                    Thread.sleep(1000); // 批次之间的间隔
                }
                
                executorService.shutdown();
                executorService.awaitTermination(30, TimeUnit.SECONDS);
                log.info("数据库连接池预热完成");
            
            } catch (Exception e) {
                log.error("数据库连接池预热异常", e);
            }
        }, "connection-pool-warmer").start();
    }
} 