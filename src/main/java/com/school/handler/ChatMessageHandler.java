package com.school.handler;

import com.alibaba.fastjson2.JSON;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.school.entity.ChatMessage;
import com.school.web.mapper.ChatMessageMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat/{userId}")
@Slf4j
public class ChatMessageHandler {

    // 由于 WebSocket 不能直接使用 Spring 注入，需要手动提供一个静态方法进行初始化
    @Setter
    private static ChatMessageMapper chatMessageMapper;

    // 存储在线用户的 WebSocket 会话 (Key=用户ID, Value=对应的 WebSocket Session)
    private static final ConcurrentHashMap<Integer, Session> onlineUsers = new ConcurrentHashMap<>();

    private Integer currentUserId; // 当前用户 ID

    // 连接建立时触发
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {

        // 当前用户的 WebSocket 会话
        this.currentUserId = userId;
        onlineUsers.put(userId, session); // 存入在线用户列表
        log.info("{} 已连接", userId);

        // 获取未读消息
        //List<ChatMessage> unreadChatMessages = chatMessageMapper.getUnreadMessages(userId);
        List<ChatMessage> unreadChatMessages = chatMessageMapper.selectList(new LambdaQueryWrapper<ChatMessage>()
                .eq(ChatMessage::getReceiverId, userId)
                .eq(ChatMessage::getStatus,3)
        );

        log.info("unreadChatMessages: {}", unreadChatMessages);

        // 发送未读消息-->用户一上线，就能看到自己未读的消息
        if (!unreadChatMessages.isEmpty()) {
            sendMessage(session, JSON.toJSONString(unreadChatMessages));
            log.info("json:{}", JSON.toJSONString(unreadChatMessages));
        }

        log.info("{} 已连接，发送未读消息：{} 条", userId, unreadChatMessages.size());
    }

    // 收到消息时触发
    @OnMessage
    public void onMessage(String messageJson) {

        // 解析前端发送的 JSON 消息
        ChatMessage chatMessage = JSON.parseObject(messageJson, ChatMessage.class);

        chatMessage.setStatus(0);
        chatMessage.setType(1);

        // 持久化存储到数据库
        chatMessageMapper.insert(chatMessage);

        // 查找接收方的 WebSocket 会话
        Session targetSession = onlineUsers.get(chatMessage.getReceiverId());

        if (targetSession != null) {
            sendMessage(targetSession, JSON.toJSONString(chatMessage)); // 发送消息给接收方
            //此时应该是收到信息了,更新信息的状态
//            chatMessageMapper.updateMessage(chatMessage.getId());
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
