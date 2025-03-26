package com.school.handler;

import com.alibaba.fastjson2.JSON;

import com.alibaba.fastjson2.JSONObject;
import com.school.entity.ChatMessage;
import com.school.web.mapper.ChatMessageMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat/{userId}")
@Slf4j
public class WebsocketChatHandler {

    // 静态注入依赖
    @Setter
    private static ChatMessageMapper chatMessageMapper;

    // 存储在线用户的 WebSocket 会话 (Key=用户ID, Value=对应的 WebSocket Session)
    private static final ConcurrentHashMap<Integer, Session> onlineUsers = new ConcurrentHashMap<>();

    private Integer currentUserId; // 当前用户 ID

    // 连接建立时触发
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {

        log.info("连接成功");

        // 当前用户的 WebSocket 会话
        this.currentUserId = userId;
        onlineUsers.put(userId, session); // 存入在线用户列表
        log.info("{} 已连接", userId);

        // 获取未读消息
//        List<ChatMessage> unreadChatMessages = chatMessageMapper.getUnreadMessages(userId);
//        List<ChatMessage> unreadChatMessages = chatMessageMapper.selectList(new LambdaQueryWrapper<ChatMessage>()
//                .eq(ChatMessage::getReceiverId, userId)
//                .eq(ChatMessage::getStatus,3)
//        );

//        log.info("unreadChatMessages: {}", unreadChatMessages);

        // 发送未读消息-->用户一上线，就能看到自己未读的消息
//        if (!unreadChatMessages.isEmpty()) {
//            sendMessage(session, JSON.toJSONString(unreadChatMessages));
//            log.info("json:{}", JSON.toJSONString(unreadChatMessages));
//        }

//        log.info("{} 已连接，发送未读消息：{} 条", userId, unreadChatMessages.size());
    }

    // 收到消息时触发
    @OnMessage
    public void onMessage(String messageJson) {
        log.info("接收到消息：{}", messageJson);

        try {
            // 检查是否是带引号的JSON字符串，需要去除外层引号
            if (messageJson.startsWith("\"") && messageJson.endsWith("\"")) {
                // 去除外层引号并解析转义字符
                messageJson = JSON.parseObject(messageJson, String.class);
            }
            
            // 先尝试将消息解析为通用JSON对象
            JSONObject jsonObject = JSON.parseObject(messageJson);
            
            // 检查消息类型
            String type = jsonObject.getString("type");
            
            // 如果是心跳消息，直接返回响应
            if ("ping".equals(type)) {
                // 可选：回复一个pong消息
                Session senderSession = onlineUsers.get(currentUserId);
                if (senderSession != null) {
                    JSONObject pongMessage = new JSONObject();
                    pongMessage.put("type", "pong");
                    pongMessage.put("timestamp", System.currentTimeMillis());
                    sendMessage(senderSession, pongMessage.toString());
                }
                return;
            }
            
            // 处理正常的聊天消息
            ChatMessage chatMessage = JSON.parseObject(messageJson, ChatMessage.class);
            
            chatMessage.setStatus(0);
            chatMessage.setType(1);

            // 持久化存储到数据库
            chatMessageMapper.insert(chatMessage);

            // 查找接收方的 WebSocket 会话
            Session targetSession = onlineUsers.get(chatMessage.getReceiverId());

            if (targetSession != null) {
                sendMessage(targetSession, JSON.toJSONString(chatMessage)); // 发送消息给接收方
                log.info("消息已成功发送");
            }
        } catch (Exception e) {
            log.error("处理消息时出错: {}", e.getMessage(), e);
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
