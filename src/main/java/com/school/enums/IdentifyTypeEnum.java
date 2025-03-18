package com.school.enums;

import lombok.Getter;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Getter
public enum IdentifyTypeEnum {
    WX("wx"),
    QQ("qq");

    private final String type;

    IdentifyTypeEnum(String type) {
        this.type = type;
    }

    public static IdentifyTypeEnum getType(String type) {
        for (IdentifyTypeEnum value : IdentifyTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
