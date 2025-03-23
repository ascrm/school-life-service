package com.school.converter;

import com.school.entity.Comment;
import com.school.entity.vo.CommentVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: ascrm
 * @Date: 2025/3/23
 */
@Mapper(componentModel = "spring")
public interface CommentConverter {
    CommentConverter INSTANCE= Mappers.getMapper(CommentConverter.class);

    CommentVo entityToVo(Comment comment);

    List<CommentVo> entityToVo(List<Comment> comments);
}
