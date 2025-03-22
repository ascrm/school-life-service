package com.school.enums;

import lombok.Getter;

/**
 * @Author: ascrm
 * @Date: 2025/3/22
 */
@Getter
public enum UserPostRelationTypeEnum {
    LIKE(1),
    FAVORITE(2);

    private final Integer type;

    UserPostRelationTypeEnum(Integer type) {
        this.type = type;
    }

    public static UserPostRelationTypeEnum getType(Integer type) {
        for (UserPostRelationTypeEnum value : UserPostRelationTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
