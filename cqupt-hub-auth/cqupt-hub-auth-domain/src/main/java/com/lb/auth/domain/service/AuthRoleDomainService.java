package com.lb.auth.domain.service;

import com.lb.auth.domain.entity.AuthRoleBO;

public interface AuthRoleDomainService {

    Boolean add(AuthRoleBO authRoleBO);

    Boolean update(AuthRoleBO authRoleBO);

    Boolean delete(AuthRoleBO authRoleBO);

}
