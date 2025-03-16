package com.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "tb_posts")
public class Posts {

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
     * 创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 创建人ID
     */
    @TableField(value = "created_by")
    private Integer createdBy;

    /**
     * 最后更新人ID
     */
    @TableField(value = "updated_by")
    private Integer updatedBy;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private Integer version;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;
} 