package com.travel.web.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.common.entity.Result;
import com.travel.converter.PostConverter;
import com.travel.entity.Image;
import com.travel.entity.Post;
import com.travel.entity.PostTag;
import com.travel.entity.User;
import com.travel.entity.dto.PostDto;
import com.travel.utils.UserHolder;
import com.travel.web.service.ImageService;
import com.travel.web.service.PostService;
import com.travel.web.service.PostTagService;
import com.travel.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
@Slf4j
public class PostController {

    private final ImageService imageService;

    private final PostService postService;

    private final PostTagService postTagService;

    private final UserService userService;

    /**
     * 发布帖子
     */
    @SneakyThrows
    @PostMapping("/post")

    public Result<String> publishPost(@RequestPart("files") MultipartFile files,
                                      @RequestParam String postsDtoStr) {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PostDto postDto = objectMapper.readValue(postsDtoStr, PostDto.class);
        Post post = PostConverter.INSTANCE.dtoToEntity(postDto);

        //上传图片到minio
        Image image = imageService.uploadImage(files);
//        if(CollectionUtils.isEmpty(image)) return Result.fail("图片上传失败");

        if(image ==null) return Result.fail("图片上传失败");

        //先保存帖子信息到数据库
        User user = userService.getUserByOpenid(UserHolder.getLoginId());
        postService.save(post.setUserId(user.getId()));

        //保存图片信息到数据库
//        image.forEach(image->{
//            image.setPostId(post.getId());
//        });
//        imageService.saveBatch(image);
        imageService.save(image.setPostId(post.getId()));

        //建立帖子和标签的关系
        if(postDto.getTagIds()!=null){
            List<PostTag> list=new ArrayList<>();
            String[] tagIds = postDto.getTagIds().split(",");
            for (String tagId : tagIds) {
                PostTag postTag = new PostTag();
                postTag.setTagId(Integer.valueOf(tagId)).setPostId(post.getId());
                list.add(postTag);
            }
            postTagService.saveBatch(list);
        }

        return Result.success("发布成功");
    }
} 