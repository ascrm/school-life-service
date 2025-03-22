package com.school.converter.decorator;

import com.school.converter.UserConverter;
import com.school.entity.User;
import com.school.entity.vo.UserVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @Author: ascrm
 * @Date: 2025/3/21
 */
@Component
public class UserConverterDecorator implements UserConverter {

    @Resource(name = "userConverterImpl")
    private UserConverter userConverter;

    @Override
    public UserVo entityToVo(User user) {
        return userConverter.entityToVo(user);
    }
}
