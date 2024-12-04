package com.lb.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.lb.auth.common.enums.AuthUserStatusEnum;
import com.lb.auth.common.enums.IsDeletedFlagEnum;
import com.lb.auth.domain.convert.AuthUserBOConverter;
import com.lb.auth.domain.entity.AuthUserBO;
import com.lb.auth.domain.service.AuthUserDomainService;
import com.lb.auth.infra.basic.entity.AuthUser;
import com.lb.auth.infra.basic.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    private final String salt = "cqupt";

    /**
     * 用户注册方法
     *
     * @param authUserBO 用户业务对象，包含用户注册所需的信息
     * @return 如果注册成功并插入数据，则返回true；否则返回false
     * @throws Exception 如果在注册过程中发生异常，则抛出异常
     */
    @Override
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), this.salt));
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authUserService.insert(authUser);
        //建立一个初步的角色的关联
        //要把当前用户的角色和权限都刷到我们的redis里
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
