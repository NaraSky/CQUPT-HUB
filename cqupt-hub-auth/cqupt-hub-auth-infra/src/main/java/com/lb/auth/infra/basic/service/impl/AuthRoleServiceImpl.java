package com.lb.auth.infra.basic.service.impl;

import com.lb.auth.infra.basic.entity.AuthRole;
import com.lb.auth.infra.basic.mapper.AuthRoleDao;
import com.lb.auth.infra.basic.service.AuthRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("authRoleService")
public class AuthRoleServiceImpl implements AuthRoleService {

    @Resource
    private AuthRoleDao authRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRole queryById(Long id) {
        return this.authRoleDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(AuthRole authRole) {
        return this.authRoleDao.insert(authRole);
    }

    /**
     * 修改数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public int update(AuthRole authRole) {
        return this.authRoleDao.update(authRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authRoleDao.deleteById(id) > 0;
    }

    /**
     * 根据条件查询角色信息
     *
     * @param authRole 查询条件对象，包含需要查询的角色信息
     * @return 返回一个符合查询条件的角色对象
     */
    @Override
    public AuthRole queryByCondition(AuthRole authRole) {
        return this.authRoleDao.queryAllByLimit(authRole);
    }

    /**
     * 根据角色ID列表查询角色信息列表
     *
     * @param roleIdList 角色ID列表
     * @return 返回与给定角色ID列表对应的角色信息列表
     */
    @Override
    public List<AuthRole> queryByRoleList(List<Long> roleIdList) {
        return authRoleDao.queryByRoleList(roleIdList);
    }

}
