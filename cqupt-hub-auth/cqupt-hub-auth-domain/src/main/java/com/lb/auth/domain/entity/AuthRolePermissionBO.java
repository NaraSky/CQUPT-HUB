package com.lb.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthRolePermissionBO implements Serializable {

    private static final long serialVersionUID = 459343371709166261L;
    
    private Long id;
    
    private Long roleId;
    
    private Long permissionId;

    private List<Long> permissionIdList;
}

