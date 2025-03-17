package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.UserAuth;
import com.travel.web.mapper.UserAuthMapper;
import com.travel.web.service.UserAuthService;
import org.springframework.stereotype.Service;

/**
 * @Author: ascrm
 * @Date: 2025/3/16
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {
}
