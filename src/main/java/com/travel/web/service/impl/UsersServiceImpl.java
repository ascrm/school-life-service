package com.travel.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.Users;
import com.travel.entity.UsersAuths;
import com.travel.utils.RedisUtil;
import com.travel.web.mapper.UsersMapper;
import com.travel.web.service.UsersAuthsService;
import com.travel.web.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

/**
 * 用户表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private final UsersAuthsService usersAuthsService;

    private final RedisUtil redisUtil;

    @Override
    public String getWxOpenid(String code) {
        // 微信登录凭证校验接口地址
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(url, Map.class);
        var result = response.getBody();

        if (result.containsKey("openid")) {
            return (String) result.get("openid");
        }
        
        throw new RuntimeException("获取微信openid失败: " + result.get("errMsg"));
    }

    @Override
    public Users getUserByOpenid(String openid) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, "wx_" + openid.substring(0, 8))
                   .eq(Users::getIsDelete, 0);
        return getOne(queryWrapper);
    }

    @Override
    public void registerWxUser(Users user, String openid) {
        UsersAuths usersAuths = new UsersAuths();
        usersAuths.setUserId(user.getId())
                  .setIdentityType("wx")
                  .setIdentifier(openid)
                  .setCredential("");
        usersAuthsService.save(usersAuths);
    }

    @Override
    public String generateToken(Users user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisUtil.setCacheObject(token, user.getId());
        return token;
    }

    @Override
    public Users getUserByToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 移除"Bearer "前缀
        }
        
        // 从缓存中获取用户ID
        Integer userId = redisUtil.getCacheObject(token);

        if (userId != null) {
            // 查询用户信息
            return getById(userId);
        }
        
        return null;
    }
}