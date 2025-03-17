package com.travel.web.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.travel.common.entity.Result;
import com.travel.entity.Users;
import com.travel.web.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.travel.common.content.AuthContent.*;

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
    public Result<String> wxLogin(@RequestParam String code,
                                  @RequestBody(required = false) Users userInfo) {
        // 1. 通过code获取微信用户的openid和session_key
        String openid = usersService.getWxOpenid(code);
        // 2. 根据openid查询用户是否已存在
        Users user = usersService.getUserByOpenid(openid);
        // 3. 用户不存在，则进行注册
        if (user == null) {
            user = new Users();
            user.setUsername(DEFAULT_USERNAME_PREFIX + openid.substring(0, 8))
                .setPassword(SaSecureUtil.md5(DEFAULT_PASSWORD))
                .setNickName(userInfo != null ? userInfo.getNickName() : DEFAULT_NICKNAME)
                .setAvatar(DEFAULT_AVATAR);
            
            // 保存用户信息
            usersService.registerWxUser(user, openid);
        }
        // 4. 登录并生成token
        StpUtil.login(openid);
        return Result.success(StpUtil.getTokenValue());
    }
}