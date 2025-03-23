package com.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_message")
public class Message {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("sender_id")
    private Integer senderId;

    @TableField("receiver_id")
    private Integer receiverId;

    @TableField("content")
    private String content;

    @TableField("type")
    private Integer type; // 1文本 2图片 3视频 4文件

    @TableField("status")
    private Integer status; // 1未送达 2已送达 3未读

    @TableField(value = "created_at",fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
