package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.travel.web.service.PostsService;
import com.travel.entity.Posts;
import com.travel.web.mapper.PostsMapper;

/**
 * 帖子信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

} 