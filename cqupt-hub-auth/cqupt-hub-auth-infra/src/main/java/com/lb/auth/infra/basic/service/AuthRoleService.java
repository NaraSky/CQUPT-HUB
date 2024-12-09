package com.lb.auth.infra.basic.service;

import com.lb.auth.infra.basic.entity.AuthRole;

import java.util.List;

public interface AuthRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRole queryById(Long id);

    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    int insert(AuthRole authRole);

    /**
     * 修改数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    int update(AuthRole authRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    AuthRole queryByCondition(AuthRole authRole);

    /**
     * 根据角色ID列表查询角色信息列表
     *
     * @param roleIdList 角色ID列表
     * @return 返回与给定角色ID列表对应的角色信息列表
     */
    List<AuthRole> queryByRoleList(List<Long> roleIdList);
}
