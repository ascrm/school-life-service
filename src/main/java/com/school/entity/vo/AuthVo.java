package com.school.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: ascrm
 * @Date: 2025/3/21
 */

@Data
@Accessors(chain = true)
public class AuthVo{

    private String token;
    private UserVo userVo;
}
