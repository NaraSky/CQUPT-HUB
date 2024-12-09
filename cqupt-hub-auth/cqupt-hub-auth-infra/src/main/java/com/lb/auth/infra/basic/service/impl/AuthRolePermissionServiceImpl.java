package com.lb.auth.infra.basic.service.impl;

import com.lb.auth.infra.basic.entity.AuthRolePermission;
import com.lb.auth.infra.basic.mapper.AuthRolePermissionDao;
import com.lb.auth.infra.basic.service.AuthRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("authRolePermissionService")
public class AuthRolePermissionServiceImpl implements AuthRolePermissionService {

    @Resource
    private AuthRolePermissionDao authRolePermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRolePermission queryById(Long id) {
        return this.authRolePermissionDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRolePermission insert(AuthRolePermission authRolePermission) {
        this.authRolePermissionDao.insert(authRolePermission);
        return authRolePermission;
    }

    @Override
    public int batchInsert(List<AuthRolePermission> authRolePermissionList) {
        return this.authRolePermissionDao.insertBatch(authRolePermissionList);
    }

    /**
     * 修改数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRolePermission update(AuthRolePermission authRolePermission) {
        this.authRolePermissionDao.update(authRolePermission);
        return this.queryById(authRolePermission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authRolePermissionDao.deleteById(id) > 0;
    }

    /**
     * 根据条件查询角色权限信息
     *
     * @param authRolePermission 查询条件对象，包含需要查询的角色权限信息
     * @return 返回一个包含查询到的角色权限信息的列表
     */
    @Override
    public List<AuthRolePermission> queryByCondition(AuthRolePermission authRolePermission) {
        return this.authRolePermissionDao.queryAllByLimit(authRolePermission);
    }

}
