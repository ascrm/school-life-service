package com.school.handler;

import com.alibaba.fastjson2.JSON;
import com.school.entity.ChatMessage;
import com.school.web.mapper.ChatMessageMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat/{userId}")
@Slf4j
public class ChatMessageHandler {

    private static ChatMessageMapper chatMessageMapper;

    // 存储在线用户的 WebSocket 会话 (Key=用户ID, Value=对应的 WebSocket Session)
    private static final ConcurrentHashMap<Integer, Session> onlineUsers = new ConcurrentHashMap<>();

    private Session session; // 当前用户的 WebSocket 会话
    private Integer currentUserId; // 当前用户 ID

    // 由于 WebSocket 不能直接使用 Spring 注入，需要手动提供一个静态方法进行初始化
    public static void setChatMessageMapper(ChatMessageMapper mapper) {
        chatMessageMapper = mapper;
    }

    // 连接建立时触发
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        this.session = session;
        this.currentUserId = userId;
        onlineUsers.put(userId, session); // 存入在线用户列表
        log.info("{} 已连接", userId);

        // 获取未读消息
        List<ChatMessage> unreadMessages = chatMessageMapper.getUnreadMessages(userId);

        log.info("unreadMessages: {}", unreadMessages);

        // 发送未读消息
        if (!unreadMessages.isEmpty()) {
            sendMessage(session, JSON.toJSONString(unreadMessages));
            log.info("json:{}", JSON.toJSONString(unreadMessages));
            // 标记这些消息为已读
            chatMessageMapper.markMessagesAsRead(userId);
        }

        log.info("{} 已连接，发送未读消息：{} 条", userId, unreadMessages.size());
    }

    // 收到消息时触发
    @OnMessage
    public void onMessage(String messageJson) {
        // 解析前端发送的 JSON 消息
        ChatMessage message = JSON.parseObject(messageJson, ChatMessage.class);
        log.info("接收到消息: {}", message);

        // 持久化存储到数据库
        chatMessageMapper.saveMessage(message);

        // 查找接收方的 WebSocket 会话
        Session targetSession = onlineUsers.get(message.getToUserId());
        log.info("目标用户 {} 的会话: {}", message.getToUserId(), targetSession);

        if (targetSession != null) {
            sendMessage(targetSession, JSON.toJSONString(message)); // 发送消息给接收方
            log.info("消息已成功发送");
        }
    }

    // 连接关闭时触发
    @OnClose
    public void onClose() {
        onlineUsers.remove(currentUserId); // 从在线列表移除
        log.info("{} 已断开", currentUserId);
    }

    // 发送消息的工具方法
    private void sendMessage(Session targetSession, String jsonStr) {
        try {
            log.info("发送消息: {}", jsonStr);
            targetSession.getBasicRemote().sendText(jsonStr);
        } catch (IOException e) {
            log.error("消息发送失败", e);
        }
    }

}
