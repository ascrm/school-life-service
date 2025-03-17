package com.travel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.String;
import java.lang.Integer;
import java.time.LocalDateTime;

/**
 * 图片信息表 实体类。
 *
 * @author ascrm
 * @since V1.0
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "tb_image")
public class Image {

    /**
     * 图片ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子ID，外键关联Posts表
     */
    @TableField(value = "post_id")
    private Integer postId;

    /**
     * 图片URL
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 上传时间
     */
    @TableField(value = "uploaded_at")
    private LocalDateTime uploadedAt;

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
