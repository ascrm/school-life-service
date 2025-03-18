package com.school.web.controller;

import com.school.entity.Tag;
import com.school.web.service.TagService;
import com.school.common.entity.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 查询所有标签信息
     */
    @GetMapping("tags")
    public Result<List<Tag>> getAllTags() {
        return Result.success(tagService.list());
    }
}