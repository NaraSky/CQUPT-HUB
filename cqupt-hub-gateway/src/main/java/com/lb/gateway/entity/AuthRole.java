package com.lb.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthRole implements Serializable {

    private static final long serialVersionUID = -529195747013808840L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色key
     */
    private String roleKey;
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
     * 是否删除 0-未删除 1-已删除
     */
    private Integer isDeleted;
}
