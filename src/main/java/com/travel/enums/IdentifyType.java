package com.travel.enums;

import lombok.Getter;

/**
 * @Author: ascrm
 * @Date: 2025/3/17
 */
@Getter
public enum IdentifyType {
    WX("wx"),
    QQ("qq");

    private final String type;

    IdentifyType(String type) {
        this.type = type;
    }

    public static IdentifyType getType(String type) {
        for (IdentifyType value : IdentifyType.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
