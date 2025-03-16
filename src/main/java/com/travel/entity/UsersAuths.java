package com.travel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户认证信息实体类
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_users_auths")  // 表名映射
public class UsersAuths implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，自增策略
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID，外键映射
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 认证类型
     */
    @TableField(value = "identity_type")
    private String identityType;

    /**
     * 唯一标识
     */
    @TableField(value = "identifier")
    private String identifier;

    /**
     * 认证凭证
     */
    @TableField("credential")
    private String credential;

    /**
     * 创建时间，自动填充
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间，自动填充
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;

    /**
     * 版本号，乐观锁注解
     */
    @Version
    @TableField("version")
    private Integer version;

    /**
     * 是否删除，逻辑删除注解
     */
    @TableLogic
    @TableField("is_delete")
    private Integer isDelete;
}