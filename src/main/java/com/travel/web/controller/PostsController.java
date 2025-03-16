package com.travel.web.controller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.travel.web.service.PostsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帖子信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Resource
    private PostsService postsService;
} 