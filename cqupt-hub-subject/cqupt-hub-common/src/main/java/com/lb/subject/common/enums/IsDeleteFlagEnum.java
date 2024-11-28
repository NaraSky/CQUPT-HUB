package com.lb.subject.common.enums;

import lombok.Getter;

@Getter
public enum IsDeleteFlagEnum {
    NORMAL(0, "正常"),
    DELETE(1, "删除");

    private Integer code;
    private String message;

    IsDeleteFlagEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static IsDeleteFlagEnum getByCode(Integer code) {
        for (IsDeleteFlagEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
