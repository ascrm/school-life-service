package com.school.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.school.web.service.CommentService;
import com.school.entity.Comment;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论表 控制层。
 *
 * @author ascrm
 * @since 1.0
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
}