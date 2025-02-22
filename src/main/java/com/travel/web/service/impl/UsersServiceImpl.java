package com.travel.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.travel.web.service.UsersService;
import com.travel.web.entity.Users;
import com.travel.web.mapper.UsersMapper;

/**
 * 用户表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}