package com.travel.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类信息表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
