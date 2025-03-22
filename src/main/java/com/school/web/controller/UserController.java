package com.school.web.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.school.common.content.AuthContent;
import com.school.common.entity.Result;
import com.school.converter.PostConverterImpl;
import com.school.converter.UserConverter;
import com.school.converter.UserConverterImpl;
import com.school.converter.decorator.PostConverterDecorator;
import com.school.converter.decorator.UserConverterDecorator;
import com.school.entity.User;
import com.school.entity.vo.AuthVo;
import com.school.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserConverterDecorator userConverterDecorator;
    
    /**
     * 微信小程序登录接口
     * 实现微信一键登录，首次登录即注册
     * 
     * @param code 微信临时登录凭证
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/user/wx/login")
    public Result<AuthVo> wxLogin(@RequestParam String code,
                                  @RequestBody(required = false) User userInfo) {
        // 1. 通过code获取微信用户的openid和session_key
        String openid = userService.getWxOpenid(code);
        // 2. 根据openid查询用户是否已存在
        User user = userService.getUserByOpenid(openid);
        // 3. 用户不存在，则进行注册
        if (user == null) {
            user = new User();
            user.setUsername(AuthContent.DEFAULT_USERNAME_PREFIX + openid.substring(0, 8))
                .setPassword(SaSecureUtil.md5(AuthContent.DEFAULT_PASSWORD))
                .setNickName(userInfo != null ? userInfo.getNickName() : AuthContent.DEFAULT_NICKNAME)
                .setAvatar(AuthContent.DEFAULT_AVATAR);
            
            // 保存用户信息
            userService.registerWxUser(user, openid);
        }
        // 4. 登录并生成token
        StpUtil.login(openid);

//        UserConverterDecorator userConverterDecorator = new UserConverterDecorator(new UserConverterImpl());
        AuthVo authVo = new AuthVo();
        authVo.setToken(StpUtil.getTokenValue())
              .setUserVo(userConverterDecorator.entityToVo(user));

        return Result.success(authVo);
    }

    @PostMapping("/user")
    public Result<String> updateUserInfo(@RequestBody User user) {
        userService.update(user, Wrappers.lambdaUpdate(User.class).eq(User::getId, user.getId()));
        return Result.success("更新成功");
    }
}