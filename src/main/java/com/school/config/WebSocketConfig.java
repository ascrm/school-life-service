package com.school.config;

import com.school.handler.ChatMessageHandler;
import com.school.web.mapper.ChatMessageMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *  WebSocket配置类
 */
@Component
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    private final ChatMessageMapper chatMessageMapper;

    @PostConstruct
    public void init() {
        ChatMessageHandler.setChatMessageMapper(chatMessageMapper);
    }
}
