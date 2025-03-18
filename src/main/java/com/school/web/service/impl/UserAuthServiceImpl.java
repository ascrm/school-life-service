package com.school.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.web.mapper.UserAuthMapper;
import com.school.entity.UserAuth;
import com.school.web.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @Author: ascrm
 * @Date: 2025/3/16
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {
}
