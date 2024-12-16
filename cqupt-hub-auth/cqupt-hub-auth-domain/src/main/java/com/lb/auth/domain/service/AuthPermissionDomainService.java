package com.lb.auth.domain.service;

import com.lb.auth.domain.entity.AuthPermissionBO;

import java.util.List;

public interface AuthPermissionDomainService {

    Boolean add(AuthPermissionBO authPermissionBO);

    Boolean update(AuthPermissionBO authPermissionBO);

    Boolean delete(AuthPermissionBO authPermissionBO);

    List<String> getPermission(String userName);

}
