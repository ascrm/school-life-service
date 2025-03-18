package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.entity.PostCategory;
import com.school.web.mapper.PostCategoryMapper;
import org.springframework.stereotype.Service;
import com.school.web.service.PostCategoryService;


/**
 * 帖子和分类的关系表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryMapper, PostCategory> implements PostCategoryService {

}