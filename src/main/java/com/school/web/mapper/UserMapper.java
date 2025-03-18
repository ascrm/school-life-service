package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
