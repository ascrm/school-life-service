package com.travel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.travel.web.service.TagsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {

    private final TagsService tagsService;
}