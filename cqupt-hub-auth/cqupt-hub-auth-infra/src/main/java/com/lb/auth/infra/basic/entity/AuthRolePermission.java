package com.lb.auth.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthRolePermission implements Serializable {

    private static final long serialVersionUID = 459343371709166261L;
    
    private Long id;
    
    private Long roleId;
    
    private Long permissionId;
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
    
    private Integer isDeleted;

}
