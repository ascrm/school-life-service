package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.school.web.service.CommentService;
import com.school.entity.Comment;
import com.school.web.mapper.CommentMapper;

/**
 * 评论表 服务层实现。
 *
 * @author ascrm
 * @since 1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}