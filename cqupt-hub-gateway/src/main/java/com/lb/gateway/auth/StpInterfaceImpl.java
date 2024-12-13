package com.lb.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lb.gateway.entity.AuthPermission;
import com.lb.gateway.entity.AuthRole;
import com.lb.gateway.redis.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
     * 根据登录ID和前缀获取授权信息
     *
     * @param loginId 登录ID
     * @param prefix  授权信息的前缀，用于区分角色授权和权限授权
     * @return 授权信息的列表，如果未找到授权信息则返回空列表
     */
    private List<String> getAuth(String loginId, String prefix) {
        // 根据登录ID和前缀构建Redis键
        String authKey = redisUtil.buildKey(prefix, loginId.toString());
        // 从Redis中获取授权值
        String authValue = redisUtil.get(authKey);

        // 如果授权值为空或仅包含空白字符，则返回空列表
        if (StringUtils.isBlank(authValue)) {
            return Collections.emptyList();
        }

        // 初始化授权信息列表
        List<String> authList = new LinkedList<>();

        // 如果前缀是角色授权前缀
        if (authRolePrefix.equals(prefix)) {
            // 将授权值解析为角色列表
            List<AuthRole> roleList = new Gson().fromJson(authValue, new TypeToken<List<AuthRole>>() {
            }.getType());
            // 将角色列表转换为角色键列表
            authList = roleList.stream().map(AuthRole::getRoleKey).collect(Collectors.toList());
        }
        // 如果前缀是权限授权前缀
        else if (authPermissionPrefix.equals(prefix)) {
            // 将授权值解析为权限列表
            List<AuthPermission> permissionList = new Gson().fromJson(authValue, new TypeToken<List<AuthPermission>>() {
            }.getType());
            // 将权限列表转换为权限键列表
            authList = permissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
        }

        // 返回授权信息列表
        return authList;
    }



}
