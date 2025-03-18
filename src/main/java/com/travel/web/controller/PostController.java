package com.travel.web.controller;

import com.travel.common.entity.Result;
import com.travel.converter.PostConverter;
import com.travel.entity.*;
import com.travel.entity.dto.PostDto;
import com.travel.utils.UserHolder;
import com.travel.web.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 帖子信息表 控制层。
 *
 * @author ascrm
 * @since V1.0
 */
@RestController
@RequestMapping("/school/web")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    private final PostTagService postTagService;

    private final UserService userService;

    private final ImageService imageService;

    /**
     * 发布帖子
     */
    @SneakyThrows
    @PostMapping("/post")
    public Result<String> publishPost(@RequestBody PostDto postDto) {
        Post post = PostConverter.INSTANCE.dtoToEntity(postDto);

        //先保存帖子信息到数据库
        User user = userService.getUserByOpenid(UserHolder.getLoginId());
        postService.save(post.setUserId(user.getId()));

        //建立帖子和标签的关系
        if(CollectionUtils.isNotEmpty(postDto.getTagIds())){
            List<PostTag> list=new ArrayList<>();
            postDto.getTagIds().forEach(tagId->{
                PostTag postTag = new PostTag();
                postTag.setTagId(tagId).setPostId(post.getId());
                list.add(postTag);
            });
            postTagService.saveBatch(list);
        }

        //保存图片
        if(CollectionUtils.isNotEmpty(postDto.getImageUrls())){
            List<Image> list=new ArrayList<>();
            postDto.getImageUrls().forEach(imageUrl->{
                Image image=new Image();
                image.setImageUrl(imageUrl).setPostId(post.getId());
                list.add(image);
            });
            imageService.saveBatch(list);
        }
        
        //使用线程池处理帖子分类任务
        postService.executeAiClassificationAsync(post,postDto.getTagIds());

        return Result.success("发布成功");
    }

    /**
     * 根据分类查询帖子
     */
    @GetMapping("/posts/tag")
    public Result<List<Post>> getPostsByCondition(String tag) {
        return null;
    }
} 