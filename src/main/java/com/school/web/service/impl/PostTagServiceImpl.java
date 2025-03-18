package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.school.web.service.PostTagService;
import com.school.entity.PostTag;
import com.school.web.mapper.PostTagMapper;


/**
 * 帖子与标签关系表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag> implements PostTagService {

}