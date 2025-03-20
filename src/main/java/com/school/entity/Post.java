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
 * 帖子信息表 实体类。
 *
 * @author ascrm
 * @since V1.0
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_post")
public class Post {

    /**
     * 帖子ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID，外键关联Users表
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 内容描述
     */
    @TableField(value = "content")
    private String content;

    /**
     * 点赞数量
     */
    @TableField(value = "likes", fill = FieldFill.INSERT)
    private int likes;

    /**
     * 收藏数量
     */
    @TableField(value = "favourite",fill = FieldFill.INSERT)
    private int favourite;

    /**
     * 评论数量
     */
    @TableField(value = "comments",fill = FieldFill.INSERT)
    private int comments;

    /**
     * 地理位置信息
     */
    @TableField(value = "location")
    private String location;

    /**
     * 额外备用字段
     */
    @TableField(value = "extra")
    private String extra;

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