package com.lb.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author lb
 * @date 2024/12/3 23:48
 * @description 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisTemplate redisTemplate;

    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.emptyList();
    }
}
