package com.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.Integer;
import java.time.LocalDateTime;

/**
 * 帖子与标签关系表 实体类。
 *
 * @author ascrm
 * @since V1.0
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_post_tag")
public class PostTag {

    /**
     * 帖子ID，外键关联Posts表
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子ID，外键关联Posts表
     */
    @TableField(value="post_id")
    private Integer postId;

    /**
     * 标签ID，外键关联Tags表
     */
    @TableField(value="tag_id")
    private Integer tagId;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 创建人ID
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 版本号
     */
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    private Integer isDelete;
}
