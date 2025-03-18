package com.school.web.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.web.mapper.UserAuthMapper;
import com.school.web.mapper.UserMapper;
import com.school.entity.User;
import com.school.entity.UserAuth;
import com.school.enums.IdentifyTypeEnum;
import com.school.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * 用户表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private final UserAuthMapper userAuthMapper;

    @Override
    public String getWxOpenid(String code) {
        // 微信登录凭证校验接口地址
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(url, String.class);
        var result = JSON.parseObject(JSON.toJSONString(response.getBody()), HashMap.class);

        if (result.containsKey("openid")) {
            return (String) result.get("openid");
        }
        
        throw new RuntimeException("获取微信openid失败: ");
    }

    @Override
    public User getUserByOpenid(String openid) {
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getIdentifier, openid));
        if(userAuth == null) return null;
        return getById(userAuth.getUserId());
    }

    @Override
    public void registerWxUser(User user, String openid) {
        save(user);
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(user.getId())
                  .setIdentityType(IdentifyTypeEnum.WX.getType())
                  .setIdentifier(openid)
                  .setCredential("");
        userAuthMapper.insert(userAuth);
    }
}