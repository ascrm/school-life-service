package com.travel.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.Tags;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签信息表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface TagsMapper extends BaseMapper<Tags> {
}
