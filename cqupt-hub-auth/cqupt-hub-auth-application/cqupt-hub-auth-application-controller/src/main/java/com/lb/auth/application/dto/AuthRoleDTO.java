package com.lb.auth.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRoleDTO implements Serializable {

    private static final long serialVersionUID = 9119276985598477283L;

    private Long id;
    
    private String roleName;
    
    private String roleKey;

}
