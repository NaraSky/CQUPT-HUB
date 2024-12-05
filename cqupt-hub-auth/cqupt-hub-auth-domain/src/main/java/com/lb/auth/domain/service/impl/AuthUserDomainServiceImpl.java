package com.lb.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.lb.auth.common.enums.AuthUserStatusEnum;
import com.lb.auth.common.enums.IsDeletedFlagEnum;
import com.lb.auth.domain.constants.AuthConstant;
import com.lb.auth.domain.convert.AuthUserBOConverter;
import com.lb.auth.domain.entity.AuthUserBO;
import com.lb.auth.domain.service.AuthUserDomainService;
import com.lb.auth.infra.basic.entity.AuthRole;
import com.lb.auth.infra.basic.entity.AuthUser;
import com.lb.auth.infra.basic.entity.AuthUserRole;
import com.lb.auth.infra.basic.service.AuthRoleService;
import com.lb.auth.infra.basic.service.AuthUserRoleService;
import com.lb.auth.infra.basic.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    private final String salt = "cqupt";

    /**
     * 用户注册方法
     *
     * @param authUserBO 用户业务对象，包含用户注册所需的信息
     * @return 如果注册成功并插入数据，则返回true；否则返回false
     * @throws Exception 如果在注册过程中发生异常，则抛出异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        // 将用户业务对象转换为实体对象
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        // 设置用户密码为加盐后的MD5值
        authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), this.salt));
        // 设置用户状态为开放
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        // 设置用户未被删除标志
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        // 插入用户数据，并返回插入记录数
        Integer count = authUserService.insert(authUser);

        // 建立一个初步的角色的关联
        // 创建一个新的角色对象
        AuthRole authRole = new AuthRole();
        // 设置角色键为普通用户
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        // 查询符合条件的角色对象
        AuthRole role = authRoleService.queryByCondition(authRole);
        // 获取角色ID
        Long roleId = role.getId();
        // 获取用户ID
        Long userId = authUser.getId();

        // 创建一个新的用户角色关联对象
        AuthUserRole authUserRole = new AuthUserRole();
        // 设置用户ID
        authUserRole.setUserId(userId);
        // 设置角色ID
        authUserRole.setRoleId(roleId);
        // 设置未被删除标志
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        // 插入用户角色关联数据
        authUserRoleService.insert(authUserRole);

        // 要把当前用户的角色和权限都刷到我们的redis里
        // 如果插入的用户记录数大于0，表示注册成功
        return count > 0;
    }


    /**
     * 更新用户信息
     *
     * @param authUserBO 用户业务对象，包含需要更新的用户信息
     * @return 如果更新成功（即至少更新了一条记录），则返回true；否则返回false
     * @throws Exception 如果在更新过程中发生任何异常，则抛出该异常
     */
    @Override
    public Boolean update(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        Integer count = authUserService.update(authUser);
        //有任何的更新，都要与缓存进行同步的修改
        return count > 0;
    }

    /**
     * 删除用户
     * 通过将用户的删除标记设置为已删除，来逻辑上删除用户。
     *
     * @param authUserBO 用户业务对象，包含需要删除的用户ID
     * @return 如果成功更新（即至少更新了一条记录），则返回true；否则返回false
     * @throws Exception 如果在更新过程中发生异常，则抛出该异常
     */
    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setId(authUserBO.getId());
        authUser.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authUserService.update(authUser);
        //有任何的更新，都要与缓存进行同步的修改
        return count > 0;
    }

}
