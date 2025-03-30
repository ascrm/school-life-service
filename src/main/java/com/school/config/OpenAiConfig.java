package com.school.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ascrm
 * @Date: 2025/3/27
 */
@Configuration
public class OpenAiConfig {

    @Bean
    public ChatClient  MyController(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
}
