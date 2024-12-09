package com.lb.auth.infra.basic.service;

import com.lb.auth.infra.basic.entity.AuthRolePermission;

import java.util.List;

public interface AuthRolePermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRolePermission queryById(Long id);

    /**
     * 新增数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    AuthRolePermission insert(AuthRolePermission authRolePermission);

    int batchInsert(List<AuthRolePermission> authRolePermissionList);

    /**
     * 修改数据
     *
     * @param authRolePermission 实例对象
     * @return 实例对象
     */
    AuthRolePermission update(AuthRolePermission authRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件查询角色权限信息
     *
     * @param authRolePermission 查询条件对象，包含需要查询的角色权限信息
     * @return 返回一个包含查询到的角色权限信息的列表
     */
    List<AuthRolePermission> queryByCondition(AuthRolePermission authRolePermission);

}
