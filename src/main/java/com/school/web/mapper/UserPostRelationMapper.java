package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.UserPostRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户帖子关系表（点赞关系或收藏关系等） 映射层。
 *
 * @author ascrm
 * @since 1.0
 */
@Mapper
public interface UserPostRelationMapper extends BaseMapper<UserPostRelation> {
}
