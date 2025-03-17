package com.travel.converter;

import com.travel.entity.Posts;
import com.travel.entity.dto.PostsDto;
import com.travel.web.controller.PostsController;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Mapper
public interface PostsConverter {

    PostsConverter INSTANCE = Mappers.getMapper(PostsConverter.class);

    Posts dtoToEntity(PostsDto postsDto);
}
