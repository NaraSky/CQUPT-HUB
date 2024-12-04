package com.lb.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.gson.Gson;
import com.lb.gateway.redis.RedisUtil;
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
    private RedisUtil redisUtil;

    private String authRolePrefix = "auth.role:";

    private String authPermissionPrefix = "auth.permission:";

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        System.out.println("123");
        return getAuth(loginId.toString(), authPermissionPrefix);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return getAuth(loginId.toString(), authRolePrefix);
    }

    /**
     * 获取指定用户的权限列表
     *
     * @param loginId 用户登录ID
     * @param prefix  权限列表的Redis前缀
     * @return 用户权限列表，如果用户没有权限，则返回空列表
     */
    private List<String> getAuth(String loginId, String prefix) {
        String authKey = redisUtil.buildKey(prefix, loginId.toString());
        String authValue = redisUtil.get(authKey);
        if (StringUtils.isBlank(authValue)) {
            return Collections.emptyList();
        }
        List<String> authList = new Gson().fromJson(authValue, List.class);
        return authList;
    }

}
