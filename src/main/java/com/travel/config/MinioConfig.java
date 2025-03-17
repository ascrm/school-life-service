package com.travel.config;

import com.travel.common.properties.MinioProperty;
import io.minio.MinioClient;
import lombok.Data;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Configuration
public class MinioConfig {
    @Bean
    public MinioClient getMinioClient(MinioProperty minioProperty){
        return MinioClient.builder()
                .endpoint(minioProperty.getUrl())
                //两个key分别是登录账号和密码
                .credentials(minioProperty.getAccessKey(), minioProperty.getSecretKey())
                .build();
    }
}
