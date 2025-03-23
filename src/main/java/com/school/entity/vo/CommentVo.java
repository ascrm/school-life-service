package com.school.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ascrm
 * @Date: 2025/3/23
 */
@Data
public class CommentVo {

    private Integer id;
    private Integer postId;
    private Integer userId;
    private Integer parentId;
    private Integer topCommentId;
    private String content;
    private Integer likes;
    private LocalDateTime createdAt;
    private String createdBy;

    private String parentName;
    private UserVo userVo;
    private List<CommentVo> children;
}
