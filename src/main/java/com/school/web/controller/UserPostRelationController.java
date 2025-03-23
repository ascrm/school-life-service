package com.school.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.school.web.service.UserPostRelationService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户帖子关系表（点赞关系或收藏关系等） 控制层。
 *
 * @author ascrm
 * @since 1.0
 */
@RestController
@RequestMapping("/userPostRelation")
@RequiredArgsConstructor
public class UserPostRelationController {

    private final UserPostRelationService userPostRelationService;
}