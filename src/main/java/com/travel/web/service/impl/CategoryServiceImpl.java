package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.travel.web.service.CategoryService;
import com.travel.entity.Category;
import com.travel.web.mapper.CategoryMapper;


/**
 * 分类信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}