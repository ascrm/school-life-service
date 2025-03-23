package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.common.entity.Result;
import com.school.entity.Message;
import com.school.entity.vo.MessageVo;

import java.util.List;

public interface ChatMessageService extends IService<Message> {
    List<Message> getChatHistory(Integer userId, Integer receiverId);

    Result<List<MessageVo>> getUnReadCount(Integer userId);
}
