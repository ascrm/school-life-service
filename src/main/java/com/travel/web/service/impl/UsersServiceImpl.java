package com.travel.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.Users;
import com.travel.web.mapper.UsersMapper;
import com.travel.web.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private final RestTemplate restTemplate = new RestTemplate();
    
    // 模拟token存储，实际项目中应该使用Redis等缓存服务
    private static final Map<String, Object> TOKEN_CACHE = new ConcurrentHashMap<>();

    @Override
    public String getWxOpenid(String code) {
        // 微信登录凭证校验接口地址
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        var response = restTemplate.getForEntity(url, Map.class);
        var result = response.getBody();
        
        if (result.containsKey("openid")) {
            return (String) result.get("openid");
        }
        
        throw new RuntimeException("获取微信openid失败: " + result.get("errMsg"));
    }

    @Override
    public Users getUserByOpenid(String openid) {
        // 根据openid查询用户信息
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        // 假设在Users表中添加了openid字段，或者使用关联表存储
        // 这里为了演示，使用username模拟查询openid
        queryWrapper.eq(Users::getUsername, "wx_" + openid.substring(0, 8))
                   .eq(Users::getIsDelete, 0);
        
        return getOne(queryWrapper);
    }

    @Override
    public boolean registerWxUser(Users user, String openid) {
        // 实际项目中，应该将openid与用户ID的关系保存到关联表中
        // 这里为了演示，直接使用username中包含openid的方式
        return save(user);
    }

    @Override
    public String generateToken(Users user) {
        // 生成唯一token
        String token = UUID.randomUUID().toString().replace("-", "");
        
        // 将token与用户信息关联存储
        TOKEN_CACHE.put(token, user.getId());
        
        return token;
    }

    @Override
    public Users getUserByToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 移除"Bearer "前缀
        }
        
        // 从缓存中获取用户ID
        Object userId = TOKEN_CACHE.get(token);
        
        if (userId != null) {
            // 查询用户信息
            return getById((Integer) userId);
        }
        
        return null;
    }
}