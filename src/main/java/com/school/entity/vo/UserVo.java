package com.school.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: ascrm
 * @Date: 2025/3/20
 */
@Data
@Accessors(chain = true)
public class UserVo {

    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private String avatar;
    private int gender;
    private String brief;
    private LocalDateTime birth;
    private String email;
    private String phone;
    private Integer status;
    private LocalDateTime createdTime;
}
