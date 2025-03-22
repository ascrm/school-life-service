package com.school.web.mapper;

import com.school.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    // 插入新消息（发送消息时调用）
    @Insert("INSERT INTO tb_chat_message (from_user_id, to_user_id, content, is_read) VALUES (#{fromUserId}, #{toUserId}, #{content}, FALSE)")
    void saveMessage(ChatMessage message);

    // 查询未读消息（用户上线时调用）
    @Select("SELECT * FROM tb_chat_message WHERE to_user_id = #{userId} AND is_read = FALSE ORDER BY timestamp ASC")
    List<ChatMessage> getUnreadMessages(Integer userId);

    // 标记消息为已读（用户上线时调用）
    @Update("UPDATE tb_chat_message SET is_read = TRUE WHERE to_user_id = #{userId} AND is_read = FALSE")
    void markMessagesAsRead(Integer userId);

    // 查询聊天历史记录（聊天窗口加载时调用）
    @Select("SELECT * FROM tb_chat_message WHERE (from_user_id = #{userId1} AND to_user_id = #{userId2}) OR (from_user_id = #{userId2} AND to_user_id = #{userId1}) ORDER BY timestamp ASC")
    List<ChatMessage> getChatHistory(Integer userId1, Integer userId2);
}
