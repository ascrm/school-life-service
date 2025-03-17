package com.travel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.travel.web.service.PostTagService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帖子与标签关系表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/postsTags")
@RequiredArgsConstructor
public class PostTagController {

    private final PostTagService postTagService;
}