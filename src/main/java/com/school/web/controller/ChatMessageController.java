package com.school.web.controller;

import com.school.common.entity.Result;
import com.school.entity.ChatMessage;
import com.school.entity.vo.MessageVo;
import com.school.web.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    /**
     * 获取两个用户之间的聊天记录（按时间排序）
     * 从查询角度来看,userId此时对应字段是的是receiver_id,而receiverId对应sender_id
     */
    @GetMapping("/chat/history")
    public Result<List<ChatMessage>> getChatHistory(@RequestParam Integer userId, @RequestParam Integer receiverId) {
        return Result.success(chatMessageService.getChatHistory(userId,receiverId));
    }

    /**
     * 获取未读的信息数
     */
    @GetMapping("/chat/unread")
    public Result<List<MessageVo>>getUnReadCount(@RequestParam Integer userId){
        return chatMessageService.getUnReadCount(userId);
    }
}
