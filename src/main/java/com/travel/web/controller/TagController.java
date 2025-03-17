package com.travel.web.controller;

import com.travel.common.entity.Result;
import com.travel.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.travel.web.service.TagService;
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