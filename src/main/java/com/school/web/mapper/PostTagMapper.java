package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.PostTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子与标签关系表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface PostTagMapper extends BaseMapper<PostTag> {
}
