package com.lb.auth.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthUserDTO implements Serializable {

    private static final long serialVersionUID = -9204098044058544260L;

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态 0:正常，1：禁用
     */
    private Integer status;
    /**
     * 简介
     */
    private String introduce;
    /**
     * 扩展字段
     */
    private String extJson;

}
