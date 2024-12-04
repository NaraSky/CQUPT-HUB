package com.lb.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthPermission implements Serializable {

    private static final long serialVersionUID = 4399749119408000805L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 父级权限id
     */
    private Long parentId;
    /**
     * 权限类型 0-菜单 1-按钮
     */
    private Integer type;
    /**
     * 菜单url
     */
    private String menuUrl;
    /**
     * 权限状态 0-正常 1-禁用
     */
    private Integer status;
    /**
     * 是否显示 0-显示 1-隐藏
     */
    private Integer show;
    /**
     * 权限图标
     */
    private String icon;
    /**
     * 权限key
     */
    private String permissionKey;
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
     * 逻辑删除标记
     */
    private Integer isDeleted;
}
