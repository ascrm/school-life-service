package com.school.converter.decorator;

import com.school.converter.PostConverter;
import com.school.converter.UserConverter;
import com.school.entity.Post;
import com.school.entity.User;
import com.school.entity.dto.PostDto;
import com.school.entity.vo.PostVo;
import com.school.entity.vo.UserVo;
import com.school.web.service.UserService;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * @Author: ascrm
 * @Date: 2025/3/20
 */
public class PostConverterDecorator implements PostConverter {

    private final PostConverter postConverter;
    private final UserConverter userConverter;

    public PostConverterDecorator(PostConverter postConverter, UserConverter userConverter) {
        this.postConverter = postConverter;
        this.userConverter = userConverter;
    }

    @Resource
    private UserService userService;

    @Override
    public Post dtoToEntity(PostDto postDto) {
        return postConverter.dtoToEntity(postDto);
    }

    @Override
    public PostVo entityToVo(Post post) {
        PostVo postVo = postConverter.entityToVo(post);
        User user = userService.getById(post.getUserId());
        UserVo userVo = userConverter.entityToVo(user);
        postVo.setUserVo(userVo);
        return postVo;
    }

    @Override
    public List<PostVo> entityToVo(List<Post> posts) {
        List<PostVo> postVoList = postConverter.entityToVo(posts);
        posts.forEach(post -> {
            PostVo postVo = this.entityToVo(post);
            postVoList.add(postVo);
        });
        return postVoList;
    }
}
