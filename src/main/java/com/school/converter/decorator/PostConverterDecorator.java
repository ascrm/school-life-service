package com.school.converter.decorator;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.school.converter.PostConverter;
import com.school.converter.UserConverter;
import com.school.entity.*;
import com.school.entity.dto.PostDto;
import com.school.entity.vo.PostVo;
import com.school.utils.UserHolder;
import com.school.web.service.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ascrm
 * @Date: 2025/3/20
 */
@Component
public class PostConverterDecorator implements PostConverter {

    @Resource(name = "postConverterImpl")
    private PostConverter postConverter;

    @Resource(name = "userConverterImpl")
    private UserConverter userConverter;

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    private UserPostRelationService userPostRelationService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private TagService tagService;

    @Resource
    private PostTagService postTagService;

    @Override
    public Post dtoToEntity(PostDto postDto) {
        return postConverter.dtoToEntity(postDto);
    }

    @Override
    public PostVo entityToVo(Post post) {
        PostVo postVo = postConverter.entityToVo(post);
        User user = userService.getById(post.getUserId());
        List<Image> imageList = imageService.list(Wrappers.<Image>lambdaQuery().eq(Image::getPostId, post.getId()));
        List<String> imageUrls = imageList.stream().map(Image::getImageUrl).toList();

        UserAuth userAuth = userAuthService.getOne(Wrappers.lambdaQuery(UserAuth.class)
                .eq(UserAuth::getIdentifier, UserHolder.getLoginId()));
        UserPostRelation relation=null;
        if(userAuth != null){
            relation = userPostRelationService.getOne(Wrappers.lambdaQuery(UserPostRelation.class)
                    .eq(UserPostRelation::getUserId, userAuth.getUserId())
                    .eq(UserPostRelation::getPostId, post.getId()));
        }

        List<PostTag> postTagList = postTagService.list(Wrappers.lambdaQuery(PostTag.class).eq(PostTag::getPostId, post.getId()));
        List<String> tagNames=null;
        if(CollectionUtils.isNotEmpty(postTagList)){
            List<Integer> postTagIds = postTagList.stream().map(PostTag::getTagId).toList();
            List<Tag> tags = tagService.listByIds(postTagIds);
            tagNames = tags.stream().map(Tag::getName).toList();
        }

        postVo.setUserVo(userConverter.entityToVo(user))
                .setImageUrls(imageUrls)
                .setIsLiked(relation != null)
                .setTagNames(tagNames);
        return postVo;
    }

    @Override
    public List<PostVo> entityToVo(List<Post> posts) {
        List<PostVo> list=new ArrayList<>();
        posts.forEach(post -> {
            PostVo postVo = this.entityToVo(post);
            list.add(postVo);
        });
        return list;
    }
}
