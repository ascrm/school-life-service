package com.school.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UnReadCount {

    private Integer senderId;

    private Integer count;

}
