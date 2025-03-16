package com.travel.web.controller;

import com.travel.entity.Result;
import com.travel.entity.Users;
import com.travel.web.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    
    /**
     * 微信小程序登录接口
     * 实现微信一键登录，首次登录即注册
     * 
     * @param code 微信临时登录凭证
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/user/wx/login")
    public Result<Map<String, Object>> wxLogin(@RequestParam String code,
                                               @RequestBody(required = false) Users userInfo) {
        // 1. 通过code获取微信用户的openid和session_key
        String openid = usersService.getWxOpenid(code);
        
        // 2. 根据openid查询用户是否已存在
        Users user = usersService.getUserByOpenid(openid);
        
        // 3. 用户不存在，则进行注册
        if (user == null) {
            user = new Users();
            // 设置基础信息
            user.setUsername("wx_" + openid.substring(0, 8))  // 生成默认用户名
                .setPassword("123456")
                .setNickName(userInfo != null ? userInfo.getNickName() : "微信用户") // 设置昵称
                .setAvatar("https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0"); // 设置头像
            
            // 保存用户信息
            usersService.registerWxUser(user, openid);
        }
        
        // 4. 生成token
        String token = usersService.generateToken(user);
        
        // 5. 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        
        return Result.success(result);
    }
    
    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/user")
    public Result<Users> getCurrentUser(@RequestHeader("Authorization") String token) {
        Users user = usersService.getUserByToken(token);
        if (user != null) {
            return Result.success(user);
        }
        return Result.unauthorized();
    }
}