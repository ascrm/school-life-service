package com.school.entity.vo;

import com.school.entity.Tag;
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
    private List<String> tagNames;
    private List<CommentVo> commentVos;
    private UserVo userVo;
}
