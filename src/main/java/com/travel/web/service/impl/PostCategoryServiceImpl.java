package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.travel.web.service.PostCategoryService;
import com.travel.entity.PostCategory;
import com.travel.web.mapper.PostCategoryMapper;


/**
 * 帖子和分类的关系表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class PostCategoryServiceImpl extends ServiceImpl<PostCategoryMapper, PostCategory> implements PostCategoryService {

}