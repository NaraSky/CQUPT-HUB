package com.lb.auth.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthPermissionDTO implements Serializable {

    private static final long serialVersionUID = 2378311883686023318L;

    private Long id;
    
    private String name;
    
    private Long parentId;
    
    private Integer type;
    
    private String menuUrl;
    
    private Integer status;
    
    private Integer show;
    
    private String icon;
    
    private String permissionKey;
}

