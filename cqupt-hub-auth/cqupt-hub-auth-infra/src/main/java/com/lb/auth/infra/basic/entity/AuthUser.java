package com.lb.auth.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 373905368939701528L;

    /**
     * id
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
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别 0:男 1:女
     */
    private Integer sex;
    /**
     *
     */
    private String avatar;
    /**
     * 状态 0:正常 1:禁用
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
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除 0:未删除 1:已删除
     */
    private Integer isDeleted;

}
