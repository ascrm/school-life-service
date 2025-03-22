package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.school.web.service.UserPostRelationService;
import com.school.entity.UserPostRelation;
import com.school.web.mapper.UserPostRelationMapper;

/**
 * 用户帖子关系表（点赞关系或收藏关系等） 服务层实现。
 *
 * @author ascrm
 * @since 1.0
 */
@Service
public class UserPostRelationServiceImpl extends ServiceImpl<UserPostRelationMapper, UserPostRelation> implements UserPostRelationService {

}