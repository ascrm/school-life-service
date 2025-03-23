package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.common.entity.Result;
import com.school.entity.Message;
import com.school.entity.vo.UnReadCount;

import java.util.List;
import java.util.Map;

public interface ChatMessageService extends IService<Message> {
    List<Message> getChatHistory(Integer userId, Integer receiverId);

    Result<List<UnReadCount>> getUnReadCount(Integer userId);
}
