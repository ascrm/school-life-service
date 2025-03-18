package com.school.converter;

import com.school.entity.Post;
import com.school.entity.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Mapper
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    Post dtoToEntity(PostDto postDto);
}
