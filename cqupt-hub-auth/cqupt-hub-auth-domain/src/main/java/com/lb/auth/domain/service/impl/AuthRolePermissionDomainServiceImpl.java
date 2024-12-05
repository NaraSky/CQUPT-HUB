package com.lb.auth.domain.service.impl;

import com.lb.auth.common.enums.IsDeletedFlagEnum;
import com.lb.auth.domain.entity.AuthRolePermissionBO;
import com.lb.auth.domain.service.AuthRolePermissionDomainService;
import com.lb.auth.infra.basic.entity.AuthRolePermission;
import com.lb.auth.infra.basic.service.AuthRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class AuthRolePermissionDomainServiceImpl implements AuthRolePermissionDomainService {

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    /**
     * 添加角色权限关系
     *
     * @param authRolePermissionBO 角色权限业务对象，包含角色ID和权限ID列表
     * @return 如果成功添加角色权限关系，则返回true；否则返回false
     */
    @Override
    public Boolean add(AuthRolePermissionBO authRolePermissionBO) {
        List<AuthRolePermission> rolePermissionList = new LinkedList<>();
        Long roleId = authRolePermissionBO.getRoleId();
        authRolePermissionBO.getPermissionIdList().forEach(permissionId -> {
            AuthRolePermission authRolePermission = new AuthRolePermission();
            authRolePermission.setRoleId(roleId);
            authRolePermission.setPermissionId(permissionId);
            authRolePermission.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            rolePermissionList.add(authRolePermission);
        });
        int count = authRolePermissionService.batchInsert(rolePermissionList);
        return count > 0;
    }


}
