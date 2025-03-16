package com.travel.web.controller;

import com.travel.entity.Result;
import com.travel.entity.Users;
import com.travel.web.service.UsersService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;
    
    /**
     * 微信小程序登录接口
     * 实现微信一键登录，首次登录即注册
     * 
     * @param code 微信临时登录凭证
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/wx-login")
    public Result<Map<String, Object>> wxLogin(@RequestParam String code,
                                               @RequestBody(required = false) Map<String, Object> userInfo) {
        // 1. 通过code获取微信用户的openid和session_key
        String openid = usersService.getWxOpenid(code);
        
        // 2. 根据openid查询用户是否已存在
        Users user = usersService.getUserByOpenid(openid);
        
        // 3. 用户不存在，则进行注册
        if (user == null) {
            user = new Users();
            // 设置基础信息
            user.setUsername("wx_" + openid.substring(0, 8))  // 生成默认用户名
                .setNickName(userInfo != null ? (String) userInfo.get("nickName") : "微信用户") // 设置昵称
                .setAvatar(userInfo != null ? (String) userInfo.get("avatarUrl") : "") // 设置头像
                .setStatus(1) // 正常状态
                .setCreatedTime(LocalDateTime.now())
                .setUpdatedTime(LocalDateTime.now())
                .setVersion(1)
                .setIsDelete(0);
            
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
    @GetMapping("/current")
    public Result<Users> getCurrentUser(@RequestHeader("Authorization") String token) {
        Users user = usersService.getUserByToken(token);
        if (user != null) {
            return Result.success(user);
        }
        return Result.unauthorized();
    }
}