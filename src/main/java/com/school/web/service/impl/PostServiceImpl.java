package com.school.web.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.entity.*;
import com.school.web.mapper.CategoryMapper;
import com.school.web.mapper.PostCategoryMapper;
import com.school.web.mapper.PostMapper;
import com.school.web.mapper.TagMapper;
import com.school.web.service.*;
import com.school.utils.OpenAiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.school.common.content.OpenAiContent.AI_SYSTEM_ROLE;
import static com.school.common.content.TimeFormatterContent.DEFAULT_DATE_FORMAT;

/**
 * 帖子信息表 服务层实现。
 *
 * @author ascrm
 * @since V1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final ExecutorService fixedThreadPool;

    private final CategoryMapper categoryMapper;

    private final PostCategoryMapper postCategoryMapper;

    private final OpenAiUtil openAiUtil;

    private final TagMapper tagMapper;

    /**
     * 使用线程池异步执行AI分类任务
     * @param post 已保存的帖子
     */
    @Override
    public void executeAiClassificationAsync(Post post,List<Integer> tagIds) {
        fixedThreadPool.submit(() -> {
            try {
                // 1. 获取所有分类
                List<Category> allCategories = categoryMapper.selectList(Wrappers.lambdaQuery(Category.class));
                if (CollectionUtils.isEmpty(allCategories)) {
                    log.warn("没有找到任何分类，无法进行AI分类");
                    return;
                }

                // 2. 构建提示词，发送给OpenAI进行分析
                StringBuilder promptBuilder = new StringBuilder();

                promptBuilder.append("请根据帖子的标题、内容、标签进行分类\n");
                if(post.getTitle()!=null) {
                    promptBuilder.append("帖子的标题是：").append(post.getTitle()).append("。\n");
                }
                if(post.getContent()!=null) {
                    promptBuilder.append("帖子的内容是：").append(post.getContent()).append("。\n");
                }
                if(CollectionUtils.isNotEmpty(tagIds)){
                    List<Tag> tags = tagMapper.selectByIds(tagIds);
                    List<String> tagNames = tags.stream().map(Tag::getName).toList();
                    promptBuilder.append("帖子的标签是：");
                    tagNames.forEach(tagName -> promptBuilder.append(tagName).append("，"));
                    promptBuilder.append("\n");
                }

                promptBuilder.append("可选的分类有：");
                // 将所有分类添加到提示词中
                allCategories.forEach(category -> promptBuilder.append(category.getId())
                        .append(".")
                        .append(category.getName())
                        .append("  ")
                );
                promptBuilder.append("\n");
                promptBuilder.append("请分析这篇帖子应该属于哪一个或多个分类，只返回分类的数字编号，多个分类用英文逗号分隔。");
                log.info("提示词：{}", promptBuilder);

                // 调用OpenAI API
                String aiResponse = openAiUtil.generation(AI_SYSTEM_ROLE,promptBuilder.toString());
                log.info("AI分类结果: {}", aiResponse);

                // 3. 解析AI返回的分类编号
                List<Integer> categoryIds = parseAiResponse(aiResponse);
                if (CollectionUtils.isEmpty(categoryIds)) {
                    log.warn("AI未能提供有效的分类结果");
                    return;
                }

                // 4. 构建并保存帖子与分类的关系
                List<PostCategory> postCategories = new ArrayList<>();
                categoryIds.forEach(categoryId->{
                    PostCategory postCategory = new PostCategory();
                    postCategory.setPostId(post.getId()).setCategoryId(categoryId);
                    postCategories.add(postCategory);
                });

                if (!postCategories.isEmpty()) {
                    postCategoryMapper.insert(postCategories);
                    log.info("帖子ID: {}，成功分配了{}个分类", post.getId(), postCategories.size());
                }
            } catch (Exception e) {
                log.error("AI分类任务执行失败", e);
            }
        });
    }

    /**
     * 解析AI返回的分类编号
     * @param aiResponse AI返回的响应
     * @return 分类编号列表
     */
    private List<Integer> parseAiResponse(String aiResponse) {
        List<Integer> result = new ArrayList<>();
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            return result;
        }

        try {
            // 移除所有非数字、逗号和空格的字符
            String cleaned = aiResponse.replaceAll("[^0-9,\\s]", "").trim();
            String[] parts = cleaned.split(",");

            for (String part : parts) {
                part = part.trim();
                if (!part.isEmpty()) {
                    result.add(Integer.parseInt(part));
                }
            }
        } catch (Exception e) {
            log.error("解析AI响应失败: {}", aiResponse, e);
        }

        return result;
    }

    /**
     * 根据标签获取随机且较新的帖子
     * @param categoryId 标签id
     * @return 帖子列表
     */
    @Override
    public List<Post> getRandomRecentPostsByTag(Integer categoryId,String earliestDateTimeStr) {
        // 根据标签查询该分类下所有帖子id
        List<Integer> postIds = postCategoryMapper.getPostIdsByCategoryId(categoryId);
        if(CollectionUtils.isEmpty(postIds)) return null;

        //初始化变量
        List<Post> postList = new ArrayList<>();
        int minAfterDays = 0;
        int maxAfterDays = 30;
        int daysSection = 10;

        //如果传入了最早的帖子日期了，那么下一次查询的帖子，最晚也应该在该日期之前
        if(earliestDateTimeStr!=null){
            LocalDateTime earliestDateTime = LocalDateTimeUtil.parse(earliestDateTimeStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
            minAfterDays =(int) ChronoUnit.DAYS.between(LocalDateTime.now(), earliestDateTime);
        }

        //根据帖子ids和其他条件查询帖子列表
        while(CollectionUtils.isEmpty(postList)){
            //设置要查询的帖子的时间，应该在 actualAfterDays 天之前
            //比如 actualAfterDays 为10，那么就应该查询10天之前的帖子，包括前11，12，13天等等
            int actualAfterDays = RandomUtil.randomInt(minAfterDays, maxAfterDays);
            LocalDateTime actualDays = LocalDateTime.now().minusDays(actualAfterDays);
            postList = list(Wrappers.<Post>lambdaQuery()
                    .in(Post::getId, postIds)
                    .le(Post::getCreatedAt, actualDays)
                    .orderByDesc(Post::getCreatedAt)
            );

            maxAfterDays-=daysSection;
            //假如我要查15天及之前发布的帖子，也就是2025年3月4号之前的帖子。但是2025年3月4号之前没有数据，
            // 则postList为空，进入下一次循环。那么减小时间范围，让得到的随机日期更小

            if(maxAfterDays==0) {
                postList = list(Wrappers.<Post>lambdaQuery()
                        .in(Post::getId, postIds)
                        .le(Post::getCreatedAt, LocalDateTime.now())
                        .orderByDesc(Post::getCreatedAt)
                );
                break;
            }
        }

        // 如果查询结果为空，直接返回
        if (CollectionUtils.isEmpty(postList)) return null;

        return postList;
    }
}