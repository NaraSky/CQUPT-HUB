package com.lb.auth.domain.service.impl;

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
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authUserService.insert(authUser);
        //建立一个初步的角色的关联
        //要把当前用户的角色和权限都刷到我们的redis里
        return count > 0;
    }
}
