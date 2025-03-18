package com.travel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.travel.web.service.PostCategoryService;
import com.travel.entity.PostCategory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帖子和分类的关系表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/postCategory")
@RequiredArgsConstructor
public class PostCategoryController {

    private final PostCategoryService postCategoryService;
}