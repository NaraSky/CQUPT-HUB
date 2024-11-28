package com.lb.subject.common.entity;

import com.lb.subject.common.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private Boolean success;

    public static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result success(T data) {
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg(ResultCodeEnum.FAIL.getMsg());
        return result;
    }

    public static <T> Result fail(T data) {
        Result result = fail();
        result.setData(data);
        return result;
    }
}
