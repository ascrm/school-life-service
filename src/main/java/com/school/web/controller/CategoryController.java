package com.school.web.controller;

import com.school.common.entity.Result;
import com.school.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.school.web.service.CategoryService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 查询所有分类
     */
    @RequestMapping("/categories")
    public Result<List<Category>> getCategoriesAll() {
        return Result.success(categoryService.list());
    }
}