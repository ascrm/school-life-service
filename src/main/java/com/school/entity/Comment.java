package com.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.String;
import java.lang.Integer;
import java.time.LocalDateTime;

/**
 * 评论表 实体类。
 *
 * @author ascrm
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_comment")
public class Comment {

    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关联的文章或帖子ID
     */
    @TableField(value = "post_id")
    private Integer postId;

    /**
     * 关联的用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 父评论ID，0表示顶级评论
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 顶级评论id
     */
    @TableField(value = "top_comment_id")
    private Integer topCommentId;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 点赞数
     */
    @TableField(value = "likes", fill = FieldFill.INSERT)
    private Integer likes;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 创建人ID
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 最后更新人ID
     */
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

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
