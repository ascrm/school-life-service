package com.travel.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.travel.admin.service.AdminsService;
import com.travel.admin.entity.Admins;
import com.travel.admin.mapper.AdminsMapper;

/**
 * 管理员表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements AdminsService {

}