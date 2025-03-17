package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.Post;
import org.springframework.stereotype.Service;
import com.travel.web.service.PostService;
import com.travel.web.mapper.PostMapper;

/**
 * 帖子信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

} 