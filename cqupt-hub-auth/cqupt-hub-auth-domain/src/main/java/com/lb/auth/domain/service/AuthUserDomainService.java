package com.lb.auth.domain.service;

import com.lb.auth.domain.entity.AuthUserBO;

public interface AuthUserDomainService {

    /**
     * 注册
     */
    Boolean register(AuthUserBO authUserBO);

}
