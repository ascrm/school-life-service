package com.travel.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签信息表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
