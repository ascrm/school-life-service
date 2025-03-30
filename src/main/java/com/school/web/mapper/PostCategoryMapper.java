package com.school.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.entity.PostCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 帖子和分类的关系表 映射层。
 *
 * @author ascrm
 * @since V1.0
 */
@Mapper
public interface PostCategoryMapper extends BaseMapper<PostCategory> {

    @Select("select tb_post_category.post_id from tb_post_category where category_id = #{categoryId}")
    List<Integer> getPostIdsByCategoryId(Integer categoryId);
}
