package com.travel.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.entity.Users;

/**
 * 用户表 服务层。
 *
 * @author ascrm
 * @since V1.0
 */
public interface UsersService extends IService<Users> {

    /**
     * 获取微信用户openid
     *
     * @param code 微信临时登录凭证
     * @return openid
     */
    String getWxOpenid(String code);

    /**
     * 根据openid查询用户
     *
     * @param openid 微信openid
     * @return 用户信息
     */
    Users getUserByOpenid(String openid);

    /**
     * 注册微信用户
     *
     * @param user   用户信息
     * @param openid 微信openid
     */
    void registerWxUser(Users user, String openid);
}