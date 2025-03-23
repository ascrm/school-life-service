package com.school.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageVo {

    private Integer senderId;

    private Integer count;

    /**
     * 前端显示
     * 1.未读数
     * 2.最新一条的消息
     */
    private String newMessage;

}
