package com.travel.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Component
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperty {

    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
