package com.travel.web.controller;

import com.travel.common.entity.Result;
import com.travel.converter.PostsConverter;
import com.travel.entity.Images;
import com.travel.entity.Posts;
import com.travel.entity.PostsTags;
import com.travel.entity.dto.PostsDto;
import com.travel.web.service.ImagesService;
import com.travel.web.service.PostsService;
import com.travel.web.service.PostsTagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
public class PostsController {

    private final ImagesService imagesService;

    private final PostsService postsService;

    private final PostsTagsService postsTagsService;

    /**
     * 发布帖子
     */
    @PostMapping("/post")
    public Result<String> publishPost(MultipartFile file, PostsDto postsDto) {
        //上传图片到minio
        String url = imagesService.uploadImage(file);
        if(url == null) return Result.fail("上传失败");

        Posts posts = PostsConverter.INSTANCE.dtoToEntity(postsDto);

        //保存图片信息到数据库
        Images images = new Images();
        images.setPostId(posts.getId()).setImageUrl(url);
        imagesService.save(images);

        //保存帖子信息到数据库
        postsService.save(posts);

        //建立帖子和标签的关系
        List<PostsTags> list=new ArrayList<>();
        postsDto.getTagIds().forEach(tagId->{
            PostsTags postsTags = new PostsTags();
            postsTags.setTagId(tagId).setPostId(posts.getId());
            list.add(postsTags);
        });
        postsTagsService.saveBatch(list);
        return Result.success("发布成功");
    }
} 