package com.school.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_follow") // 指定表名
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO) // 主键自增
    private Integer id;

    @TableField("follower_id")
    private Integer followerId; // 关注者ID

    @TableField("followee_id")
    private Integer followeeId; // 被关注者ID

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt; // 创建时间

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt; // 更新时间

    @Version
    @TableField("version")
    private Integer version; // 版本号（用于乐观锁）

    @TableLogic
    @TableField("is_delete")
    private Boolean isDelete; // 逻辑删除（1表示已删除，0表示未删除）
}