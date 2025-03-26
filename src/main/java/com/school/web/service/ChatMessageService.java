package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.common.entity.Result;
import com.school.entity.ChatMessage;
import com.school.entity.vo.MessageVo;

import java.util.List;

public interface ChatMessageService extends IService<ChatMessage> {
    List<ChatMessage> getChatHistory(Integer userId, Integer receiverId);

    Result<List<MessageVo>> getUnReadCount(Integer userId);
}
