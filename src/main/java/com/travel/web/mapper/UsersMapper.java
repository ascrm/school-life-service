package com.travel.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.web.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
}
