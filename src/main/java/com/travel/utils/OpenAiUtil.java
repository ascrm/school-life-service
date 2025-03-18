package com.travel.utils;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import static com.travel.common.content.OpenAiContent.AI_SYSTEM_ROLE;

/**
 * @Author: ascrm
 * @Date: 2025/3/18
 */
@Component
public class OpenAiUtil {

    private final ChatClient chatClient;

    public OpenAiUtil(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generation(String message) {
        return this.chatClient.prompt()
                .user(message)
                .system(AI_SYSTEM_ROLE)
                .call()
                .content();
    }
}
