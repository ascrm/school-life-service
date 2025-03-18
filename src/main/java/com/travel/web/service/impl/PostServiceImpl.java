package com.travel.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.entity.Category;
import com.travel.entity.Post;
import com.travel.entity.PostCategory;
import com.travel.entity.Tag;
import com.travel.utils.OpenAiUtil;
import com.travel.web.service.CategoryService;
import com.travel.web.service.PostCategoryService;
import com.travel.web.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.travel.web.service.PostService;
import com.travel.web.mapper.PostMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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

    private final CategoryService categoryService;

    private final PostCategoryService postCategoryService;

    private final OpenAiUtil openAiUtil;

    private final TagService tagService;

    /**
     * 使用线程池异步执行AI分类任务
     * @param post 已保存的帖子
     */
    @Override
    public void executeAiClassificationAsync(Post post,List<Integer> tagIds) {
        fixedThreadPool.submit(() -> {
            try {
                // 1. 获取所有分类
                List<Category> allCategories = categoryService.list();
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
                    List<Tag> tags = tagService.listByIds(tagIds);
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
                String aiResponse = openAiUtil.generation(promptBuilder.toString());
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
                    postCategoryService.saveBatch(postCategories);
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
}