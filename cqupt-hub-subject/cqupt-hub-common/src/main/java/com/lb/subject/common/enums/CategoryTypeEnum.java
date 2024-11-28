package com.lb.subject.common.enums;

import lombok.Getter;

@Getter
public enum CategoryTypeEnum {
    PRIMARY(1, "一级分类"),
    SECONDARY(2, "二级分类");

    private Integer code;
    private String message;

    CategoryTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CategoryTypeEnum getByCode(Integer code) {
        for (CategoryTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
