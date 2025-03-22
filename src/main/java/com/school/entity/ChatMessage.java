package com.school.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private Integer fromUserId;  // 发送者
    private Integer toUserId;    // 接收者
    private String content;     // 消息内容

}


