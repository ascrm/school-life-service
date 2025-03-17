package com.travel.web.controller;

import com.travel.common.entity.Result;
import com.travel.entity.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 帖子信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
public class PostsController {

    /**
     * 发布帖子
     */
    @PostMapping("/post")
    public Result<String> publishPost(MultipartFile file, Posts posts) {
        return null;
    }
} 