package com.school.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.school.entity.Image;
import com.school.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ascrm
 * @Date: 2025/3/20
 */
@Data
@Accessors(chain = true)
public class PostVo {
    private Integer id;
    private String title;
    private String content;
    private int likes;
    private int favorites;
    private int comments;
    private String location;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<String> imageUrls;
    private Boolean isLiked;

    private UserVo userVo;
}
