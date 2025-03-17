package com.travel.web.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.Users;
import com.travel.entity.UsersAuths;
import com.travel.enums.IdentifyType;
import com.travel.utils.RedisUtil;
import com.travel.web.mapper.UsersAuthsMapper;
import com.travel.web.mapper.UsersMapper;
import com.travel.web.service.UsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private final UsersAuthsMapper usersAuthsMapper;

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
        var response = restTemplate.getForEntity(url, String.class);
        var result = JSON.parseObject(JSON.toJSONString(response.getBody()), HashMap.class);

        if (result.containsKey("openid")) {
            return (String) result.get("openid");
        }
        
        throw new RuntimeException("获取微信openid失败: ");
    }

    @Override
    public Users getUserByOpenid(String openid) {
        UsersAuths usersAuths = usersAuthsMapper.selectOne(new LambdaQueryWrapper<UsersAuths>()
                .eq(UsersAuths::getIdentifier, openid));
        if(usersAuths == null) return null;
        return getById(usersAuths.getUserId());
    }

    @Override
    public void registerWxUser(Users user, String openid) {
        save(user);
        UsersAuths usersAuths = new UsersAuths();
        usersAuths.setUserId(user.getId())
                  .setIdentityType(IdentifyType.WX.getType())
                  .setIdentifier(openid)
                  .setCredential("");
        usersAuthsMapper.insert(usersAuths);
    }
}