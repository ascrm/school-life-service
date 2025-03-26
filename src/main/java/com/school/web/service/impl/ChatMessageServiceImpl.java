package com.school.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.common.entity.Result;
import com.school.entity.ChatMessage;
import com.school.entity.vo.MessageVo;
import com.school.web.mapper.ChatMessageMapper;
import com.school.web.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage>implements ChatMessageService {

    private final ChatMessageMapper chatMessageMapper;

    @Override
    public List<ChatMessage> getChatHistory(Integer userId, Integer receiverId) {
        log.info("查询聊天记录: 用户 {} <-> {}", userId, receiverId);

        //此时是点进聊天框了,将数据库对应的未读信息更新为已读
        //获取用户之间未读消息
        List<Integer> ids = chatMessageMapper.getUnReadMessage(userId,receiverId);

        for (Integer id : ids) {
            chatMessageMapper.updateMessage(id);
        }

        return chatMessageMapper.selectList(new LambdaQueryWrapper<ChatMessage>()
                .and(wrapper -> wrapper
                        .eq(ChatMessage::getSenderId, userId).eq(ChatMessage::getReceiverId, receiverId)
                        .or()
                        .eq(ChatMessage::getSenderId, receiverId).eq(ChatMessage::getReceiverId, userId))
                .orderByAsc(ChatMessage::getCreatedAt)
        );
    }

    @Override
    public Result<List<MessageVo>> getUnReadCount(Integer userId) {

        //当前用户作为接受者receiver
        List<MessageVo> list = chatMessageMapper.getUnReadCount(userId);
        log.info("unreadCount: {}", list);
        for (MessageVo messageVo : list) {
            String latestMessage = chatMessageMapper.getLatestMessage(messageVo.getSenderId());
            messageVo.setNewMessage(latestMessage);
        }
        return Result.success(list);
    }
}
