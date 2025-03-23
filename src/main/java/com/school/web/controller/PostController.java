package com.school.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.school.common.entity.Result;
import com.school.converter.PostConverter;
import com.school.converter.decorator.CommentConverterDecorator;
import com.school.converter.decorator.PostConverterDecorator;
import com.school.entity.*;
import com.school.entity.vo.CommentVo;
import com.school.entity.vo.PostVo;
import com.school.enums.UserPostRelationTypeEnum;
import com.school.web.service.*;
import com.school.entity.dto.PostDto;
import com.school.utils.UserHolder;
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

    private final PostConverterDecorator postConverterDecorator;

    private final UserPostRelationService userPostRelationService;

    private final UserAuthService userAuthService;

    private final CommentService commentService;

    private final CommentConverterDecorator commentConverterDecorator;

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
    @GetMapping("/posts/category")
    public Result<List<PostVo>> getPostsByCondition(@RequestParam Integer categoryId,
                                                    @RequestParam(required = false) String earliestDateTimeStr) {
        List<Post> postList = postService.getRandomRecentPostsByTag(categoryId, earliestDateTimeStr);
        List<PostVo> postVos = postConverterDecorator.entityToVo(postList);
        return Result.success(postVos);
    }

    /**
     * 帖子点赞
     */
    @GetMapping("/post/like")
    public Result<String> addLikePost(Integer postId) {

        UserPostRelation userPostRelation = new UserPostRelation();
        UserAuth userAuth = userAuthService.getOne(Wrappers.lambdaQuery(UserAuth.class).eq(UserAuth::getIdentifier, UserHolder.getLoginId()));
        UserPostRelation relation = userPostRelationService.getOne(Wrappers.lambdaQuery(UserPostRelation.class)
                .eq(UserPostRelation::getPostId, postId)
                .eq(UserPostRelation::getUserId, userAuth.getUserId()));

        if (relation == null) {
            postService.update(Wrappers.lambdaUpdate(Post.class).eq(Post::getId, postId).setSql("likes=likes+1"));
            userPostRelation.setPostId(postId)
                    .setUserId(userAuth.getUserId())
                    .setRelationType(UserPostRelationTypeEnum.LIKE.getType());
            userPostRelationService.save(userPostRelation);
            return Result.success("点赞成功");
        }else{
            postService.update(Wrappers.lambdaUpdate(Post.class).eq(Post::getId, postId).setSql("likes=likes-1"));
            userPostRelationService.remove(Wrappers.lambdaQuery(UserPostRelation.class)
                    .eq(UserPostRelation::getPostId, postId)
                    .eq(UserPostRelation::getUserId,userAuth.getUserId()));
            return Result.success("取消点赞成功");
        }
    }

    /**
     * 根据帖子id查询帖子详情
     */
    @GetMapping("/post/id")
    public Result<PostVo> getPostById(Integer postId){
        Post post = postService.getById(postId);

        List<Comment> commentList = commentService.list(Wrappers.lambdaQuery(Comment.class).eq(Comment::getPostId, postId));
        List<CommentVo> commentVos = commentConverterDecorator.entityToVo(commentList);
        PostVo postVo = postConverterDecorator.entityToVo(post);
        postVo.setCommentVos(commentVos);
        return Result.success(postVo);
    }
} 