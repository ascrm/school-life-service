package com.travel.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.entity.Image;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图片信息表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface ImageMapper extends BaseMapper<Image> {
}
