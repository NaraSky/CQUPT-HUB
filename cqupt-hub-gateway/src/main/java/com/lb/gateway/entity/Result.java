package com.lb.gateway.entity;

import com.lb.gateway.enums.ResultCodeEnum;
import lombok.Data;
import lombok.val;

@Data
public class Result<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    public static Result ok() {
        val result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getDesc());
        return result;
    }

    public static <T> Result ok(T data) {
        val result = ok();
        result.setData(data);
        return result;
    }

    public static Result fail() {
        val result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(ResultCodeEnum.FAIL.getDesc());
        return result;
    }

    public static <T> Result fail(T data) {
        val result = fail();
        result.setData(data);
        return result;
    }

    public static Result fail(Integer code,String message) {
        val result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
