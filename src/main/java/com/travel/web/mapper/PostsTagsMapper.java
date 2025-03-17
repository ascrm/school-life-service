package com.travel.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.PostsTags;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子与标签关系表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface PostsTagsMapper extends BaseMapper<PostsTags> {
}
