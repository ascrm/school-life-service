package com.school.converter;

import com.school.converter.decorator.PostConverterDecorator;
import com.school.converter.decorator.UserConverterDecorator;
import com.school.entity.User;
import com.school.entity.vo.UserVo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: ascrm
 * @Date: 2025/3/20
 */
@Mapper
@DecoratedWith(UserConverterDecorator.class)
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserVo entityToVo(User user);
}
