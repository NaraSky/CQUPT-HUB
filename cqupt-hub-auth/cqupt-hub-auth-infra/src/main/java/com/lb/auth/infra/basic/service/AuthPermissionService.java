package com.lb.auth.infra.basic.service;

import com.lb.auth.infra.basic.entity.AuthPermission;

import java.util.List;

public interface AuthPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthPermission queryById(Long id);


    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    int insert(AuthPermission authPermission);

    /**
     * 修改数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    int update(AuthPermission authPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据角色ID列表查询权限信息列表
     *
     * @param roleIdList 角色ID列表
     * @return 返回一个包含查询到的权限信息列表的集合
     */
    List<AuthPermission> queryByRoleList(List<Long> roleIdList);

}
