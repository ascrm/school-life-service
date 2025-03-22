package com.school.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回给前端的粉丝VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowVO {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

}
