package com.lb.auth.infra.basic.service.impl;

import com.lb.auth.infra.basic.entity.AuthPermission;
import com.lb.auth.infra.basic.mapper.AuthPermissionDao;
import com.lb.auth.infra.basic.service.AuthPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("authPermissionService")
public class AuthPermissionServiceImpl implements AuthPermissionService {

    @Resource
    private AuthPermissionDao authPermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthPermission queryById(Long id) {
        return this.authPermissionDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(AuthPermission authPermission) {
        return this.authPermissionDao.insert(authPermission);
    }

    /**
     * 修改数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    @Override
    public int update(AuthPermission authPermission) {
        return this.authPermissionDao.update(authPermission);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authPermissionDao.deleteById(id) > 0;
    }

    /**
     * 根据角色ID列表查询权限信息
     *
     * @param roleIdList 角色ID列表
     * @return 返回与给定角色ID列表对应的权限信息列表
     */
    @Override
    public List<AuthPermission> queryByRoleList(List<Long> roleIdList) {
        return this.authPermissionDao.queryByRoleList(roleIdList);
    }

}
