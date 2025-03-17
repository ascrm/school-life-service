package com.travel.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.travel.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static com.travel.common.content.AuthContent.USER_HOLDER_KEY;

@Configuration
@Slf4j
public class WevMVcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
        log.info("WevMVcConfig.addInterceptors.URL: {}", SaHolder.getRequest().getUrl());
            SaRouter
                .match("/**")
                .notMatch("/school/web/user/wx/login")
                .check(r -> {
                    StpUtil.checkLogin();
                    UserHolder.set(USER_HOLDER_KEY, StpUtil.getLoginIdAsString());
                });
        })).addPathPatterns("/**");
    }
}
