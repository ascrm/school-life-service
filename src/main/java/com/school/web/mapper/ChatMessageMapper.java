package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.school.entity.Message;
import com.school.entity.vo.UnReadCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatMessageMapper extends BaseMapper<Message> {

    // 标记消息为已读（用户上线时调用）
    @Update("UPDATE tb_message SET status = 3 WHERE receiver_id = #{userId} AND status = 0")
    void markMessagesAsRead(Integer userId);

    @Update("update tb_message set status = 1 where id=#{id}")
    void updateMessage(Integer id);


    @Select("select id from tb_message where receiver_id=#{userId} and sender_id=#{receiverId} and status=0")
    List<Integer> getUnReadMessage(Integer userId, Integer receiverId);

    @Select("select sender_id,count(sender_id) count from tb_message " +
            "where receiver_id=#{userId} and status=0 group by sender_id")
    List<UnReadCount> getUnReadCount(Integer userId);

//    // 查询聊天历史记录（聊天窗口加载时调用）
//    @Select("SELECT * FROM tb_message WHERE (from_user_id = #{userId1} AND to_user_id = #{userId2}) OR (from_user_id = #{userId2} AND to_user_id = #{userId1}) ORDER BY timestamp ASC")
//    List<Message> getChatHistory(Integer userId1, Integer userId2);
}
