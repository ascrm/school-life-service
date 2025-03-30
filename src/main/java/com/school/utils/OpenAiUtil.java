package com.school.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

/**
 * @Author: ascrm
 * @Date: 2025/3/18
 */
@Component
@Slf4j
public class OpenAiUtil {

    @Resource
    private ChatClient chatClient;

    public String generation(String aiRole,String message) {
        return chatClient.prompt()
                .user(message)
                .system(aiRole)
                .call()
                .content();
    }
}
