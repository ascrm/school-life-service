package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表 映射层。
 *
 * @author ascrm
 * @since 1.0
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
