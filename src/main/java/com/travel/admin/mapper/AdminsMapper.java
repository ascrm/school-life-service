package com.travel.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.admin.entity.Admins;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface AdminsMapper extends BaseMapper<Admins> {
}
