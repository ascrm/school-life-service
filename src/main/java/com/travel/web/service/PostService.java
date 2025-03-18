package com.travel.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.entity.Post;

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
    void executeAiClassificationAsync(Post post);
} 