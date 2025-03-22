package com.school.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private Integer fromUserId;  // 发送者
    private Integer toUserId;    // 接收者
    private String content;      // 消息内容
    private LocalDateTime timestamp;  // 发送时间
    private Boolean isRead;      // 是否已读
}


