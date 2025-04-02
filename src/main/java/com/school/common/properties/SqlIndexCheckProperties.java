package com.school.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sql.index.check")
public class SqlIndexCheckProperties {

    private boolean enabled;
    private boolean logOnlyNoIndex;
    private boolean includeFullExplain;
} 