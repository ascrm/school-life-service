package com.school.converter.decorator;

import com.school.converter.UserConverter;
import com.school.entity.User;
import com.school.entity.vo.UserVo;

/**
 * @Author: ascrm
 * @Date: 2025/3/21
 */
public class UserConverterDecorator implements UserConverter {

    private final UserConverter userConverter;

    public UserConverterDecorator(UserConverter userConverter) {
        this.userConverter=userConverter;
    }

    @Override
    public UserVo entityToVo(User user) {
        return userConverter.entityToVo(user);
    }
}
