package com.travel.converter;

import com.travel.entity.Post;
import com.travel.entity.dto.PostDto;
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
