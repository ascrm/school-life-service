package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.UsersAuths;
import com.travel.web.mapper.UsersAuthsMapper;
import com.travel.web.service.UsersAuthsService;
import org.springframework.stereotype.Service;

/**
 * @Author: ascrm
 * @Date: 2025/3/16
 */
@Service
public class UsersAuthsServiceImpl extends ServiceImpl<UsersAuthsMapper, UsersAuths> implements UsersAuthsService {
}
