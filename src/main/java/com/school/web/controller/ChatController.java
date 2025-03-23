package com.school.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.school.common.entity.Result;
import com.school.entity.Message;
import com.school.entity.vo.UnReadCount;
import com.school.web.mapper.ChatMessageMapper;
import com.school.web.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageService chatMessageService;

    /**
     * 获取两个用户之间的聊天记录（按时间排序）
     * @param userId 用户 ID
     * @param receiverId 好友 ID
     *
     * 从查询角度来看,userId此时对应字段是的是receiver_id,而receiverId对应sender_id
     *
     * @return 聊天记录
     */
    @GetMapping("history")
    public List<Message> getChatHistory(@RequestParam Integer userId, @RequestParam Integer receiverId) {


        return chatMessageService.getChatHistory(userId,receiverId);


    }


    /**
     * 获取未读的信息数
     * @return
     */
    @GetMapping("/unread")
    public Result<List<UnReadCount>>getUnReadCount(@RequestParam Integer userId){
        return chatMessageService.getUnReadCount(userId);
    }

}
