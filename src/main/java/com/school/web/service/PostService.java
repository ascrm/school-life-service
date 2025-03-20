package com.school.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.school.entity.Post;

import java.util.List;

/**
 * 帖子信息表 服务层。
 *
 * @author ascrm
 * @since V1.0
 */
public interface PostService extends IService<Post> {

    /**
     * 使用线程池异步执行AI分类任务
     * @param post 已保存的帖子
     */
    void executeAiClassificationAsync(Post post, List<Integer> tagIds);
    
    /**
     * 根据标签获取随机且较新的帖子
     * @param tagId 标签id
     * @return 帖子列表
     */
    List<Post> getRandomRecentPostsByTag(Integer tagId,String earliestDateTimeStr);
} 