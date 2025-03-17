package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.travel.web.service.PostsTagsService;
import com.travel.entity.PostsTags;
import com.travel.web.mapper.PostsTagsMapper;


/**
 * 帖子与标签关系表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class PostsTagsServiceImpl extends ServiceImpl<PostsTagsMapper, PostsTags> implements PostsTagsService {

}