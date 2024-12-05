package com.lb.auth.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRoleBO implements Serializable {

    private Long id;
    
    private String roleName;
    
    private String roleKey;
}

