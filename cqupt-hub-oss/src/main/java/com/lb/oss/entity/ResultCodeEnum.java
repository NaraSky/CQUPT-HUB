package com.lb.oss.entity;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    public final int code;
    public final String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCodeEnum getByCode(int code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.getCode() == code) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
