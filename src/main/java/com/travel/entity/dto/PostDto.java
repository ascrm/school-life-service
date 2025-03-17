package com.travel.entity.dto;

import lombok.Data;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Data
public class PostDto {

    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private String tagIds;
}
