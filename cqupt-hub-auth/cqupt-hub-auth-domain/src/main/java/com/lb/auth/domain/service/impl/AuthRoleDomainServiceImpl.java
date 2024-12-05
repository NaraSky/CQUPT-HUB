package com.lb.auth.domain.service.impl;

import com.lb.auth.common.enums.IsDeletedFlagEnum;
import com.lb.auth.domain.convert.AuthRoleBOConverter;
import com.lb.auth.domain.entity.AuthRoleBO;
import com.lb.auth.domain.service.AuthRoleDomainService;
import com.lb.auth.infra.basic.entity.AuthRole;
import com.lb.auth.infra.basic.service.AuthRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AuthRoleDomainServiceImpl implements AuthRoleDomainService {

    @Resource
    private AuthRoleService authRoleService;

    /**
     * 添加角色
     *
     * @param authRoleBO 业务对象，包含要添加的角色信息
     * @return 添加成功返回true，否则返回false
     */
    @Override
    public Boolean add(AuthRoleBO authRoleBO) {
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        authRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authRoleService.insert(authRole);
        return count > 0;
    }

    /**
     * 更新角色信息
     *
     * @param authRoleBO 业务对象，包含要更新的角色信息
     * @return 更新成功返回true，否则返回false
     */
    @Override
    public Boolean update(AuthRoleBO authRoleBO) {
        AuthRole authRole = AuthRoleBOConverter.INSTANCE.convertBOToEntity(authRoleBO);
        Integer count = authRoleService.update(authRole);
        return count > 0;
    }

    /**
     * 删除角色
     *
     * @param authRoleBO 业务对象，包含要删除的角色ID
     * @return 删除成功返回true，否则返回false
     */
    @Override
    public Boolean delete(AuthRoleBO authRoleBO) {
        AuthRole authRole = new AuthRole();
        authRole.setId(authRoleBO.getId());
        authRole.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authRoleService.update(authRole);
        return count > 0;
    }

}
