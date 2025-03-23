package com.school.converter.decorator;

import com.school.converter.CommentConverter;
import com.school.converter.UserConverter;
import com.school.entity.Comment;
import com.school.entity.User;
import com.school.entity.vo.CommentVo;
import com.school.entity.vo.UserVo;
import com.school.web.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.*;
/**
 * @Author: ascrm
 * @Date: 2025/3/23
 */
@Component
public class CommentConverterDecorator implements CommentConverter{

    @Resource(name="commentConverterImpl")
    private CommentConverter commentConverter;

    @Resource(name="userConverterImpl")
    private UserConverter userConverter;

    @Resource
    private UserService userService;

    @Override
    public CommentVo entityToVo(Comment comment) {
        User user = userService.getById(comment.getUserId());
        UserVo userVo = userConverter.entityToVo(user);
        CommentVo commentVo = commentConverter.entityToVo(comment);
        commentVo.setUserVo(userVo);
        return commentVo;
    }

    @Override
    public List<CommentVo> entityToVo(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 1. 转换所有评论为CommentVo
        Map<Integer, CommentVo> commentVoMap = new HashMap<>();
        for (Comment comment : comments) {
            CommentVo commentVo = entityToVo(comment);
            // 确保children字段已初始化
            if (commentVo.getChildren() == null) {
                commentVo.setChildren(new ArrayList<>());
            }
            commentVoMap.put(comment.getId(), commentVo);
        }
        
        // 2. 按照树形结构组织评论
        List<CommentVo> rootComments = new ArrayList<>();
        
        for (Comment comment : comments) {
            CommentVo commentVo = commentVoMap.get(comment.getId());
            
            // 如果是顶级评论
            if (Objects.equals(comment.getParentId(), comment.getId())) {
                rootComments.add(commentVo);
            } else {
                // 是回复评论，找到其父评论
                CommentVo parentComment = commentVoMap.get(comment.getParentId());
                User user = userService.getById(parentComment.getUserId());
                commentVo.setParentName(user.getNickName());

                //然后将回复评论添加到顶级评论下面
                CommentVo topCommentVo = commentVoMap.get(commentVo.getTopCommentId());
                topCommentVo.getChildren().add(commentVo);
            }
        }
        
        return rootComments;
    }
}
