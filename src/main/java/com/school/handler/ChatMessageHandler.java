package com.school.handler;

import com.alibaba.fastjson2.JSON;
import com.school.entity.ChatMessage;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat/{userId}")
public class ChatMessageHandler {

    // 在线用户表：记录当前所有在线的用户会话（Key=用户ID，Value=对应的WebSocket会话）
    private static final ConcurrentHashMap<Integer, Session> onlineUsers = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ChatMessageHandler.class);

    private Session session;   // 当前用户的WebSocket会话
    private Integer currentUserId; // 当前用户ID（从路径参数获取）

    // 连接建立时触发
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) throws IOException {
        // 先检查是否已有旧连接，防止重复登录问题
        if (onlineUsers.containsKey(userId)) {
            onlineUsers.get(userId).close();
        }

        this.session = session;
        this.currentUserId = userId;
        onlineUsers.put(userId, session); // 存入在线用户列表
        System.out.println(userId + " 已连接");
    }

    // 收到消息时触发
    @OnMessage
    public void onMessage(String messageJson) {


        // 解析前端发送的JSON消息
        ChatMessage message = JSON.parseObject(messageJson, ChatMessage.class);
        log.info("message:{}", message);
        // 核心逻辑：找到接收方的会话并转发消息
        Session targetSession = onlineUsers.get(message.getToUserId());
        log.info("targetSession:{}", targetSession);
        if (targetSession != null) {
            sendMessage(targetSession, message); // 发送消息给接收方
            log.info("发送成功");
        }
    }

    // 连接关闭时触发
    @OnClose
    public void onClose() {
        onlineUsers.remove(currentUserId); // 从在线列表移除
        System.out.println(currentUserId + " 已断开");
    }

    // 发送消息的工具方法
    private void sendMessage(Session targetSession, ChatMessage message) {
        try {
            String jsonStr = JSON.toJSONString(message);
            log.info("jsonStr:{}", jsonStr);
            targetSession.getBasicRemote().sendText(jsonStr);
            log.info("发出了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
