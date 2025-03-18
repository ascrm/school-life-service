package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.entity.Category;
import com.school.web.mapper.CategoryMapper;
import com.school.web.service.CategoryService;
import org.springframework.stereotype.Service;


/**
 * 分类信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}