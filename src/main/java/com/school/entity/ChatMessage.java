package com.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_chat_message")
public class ChatMessage {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("sender_id")
    private Integer senderId;

    @TableField("receiver_id")
    private Integer receiverId;

    @TableField("content")
    private String content;

    @TableField("type")
    private Integer type; // 1 文本 2 图片 3 视频 4 文件

    @TableField("status")
    private Integer status; // 1未读 2已读

    @TableField(value = "created_at",fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
