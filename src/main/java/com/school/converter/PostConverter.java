package com.school.converter;

import com.school.converter.decorator.PostConverterDecorator;
import com.school.entity.Post;
import com.school.entity.dto.PostDto;
import com.school.entity.vo.PostVo;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Mapper
@DecoratedWith(PostConverterDecorator.class)
public interface PostConverter {
    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    Post dtoToEntity(PostDto postDto);

    PostVo entityToVo(Post post);

    List<PostVo> entityToVo(List<Post> posts);
}
