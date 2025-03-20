package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子信息表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

} 