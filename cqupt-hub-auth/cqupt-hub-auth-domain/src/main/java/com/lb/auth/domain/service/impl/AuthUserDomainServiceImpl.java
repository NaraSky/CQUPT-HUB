package com.lb.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.gson.Gson;
import com.lb.auth.common.enums.AuthUserStatusEnum;
import com.lb.auth.common.enums.IsDeletedFlagEnum;
import com.lb.auth.domain.constants.AuthConstant;
import com.lb.auth.domain.convert.AuthUserBOConverter;
import com.lb.auth.domain.entity.AuthUserBO;
import com.lb.auth.domain.redis.RedisUtil;
import com.lb.auth.domain.service.AuthUserDomainService;
import com.lb.auth.infra.basic.entity.*;
import com.lb.auth.infra.basic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    private final String salt = "cqupt";

    @Resource
    private RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    private static final String LOGIN_PREFIX = "loginCode";

    /**
     * 用户注册方法
     *
     * @param authUserBO 用户业务对象，包含用户注册所需的信息
     * @return 如果注册成功并插入数据，则返回true；否则返回false
     * <p>
     * 方法说明：
     * 1. 将用户业务对象转换为实体对象，并设置密码、状态、删除标识等属性。
     * 2. 将用户信息插入到数据库中，并获取用户ID。
     * 3. 创建一个普通用户角色对象，并根据条件查询对应的角色ID。
     * 4. 创建一个用户角色关联对象，设置用户ID和角色ID，并插入到数据库中。
     * 5. 构建角色键，将角色信息以JSON格式存入Redis中。
     * 6. 创建一个角色权限对象，设置角色ID，并查询该角色的所有权限。
     * 7. 将查询到的权限ID列表转换为权限对象列表，并将权限信息以JSON格式存入Redis中。
     * 8. 返回插入的用户数量，若大于0则表示注册成功，否则表示注册失败。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        // 设置密码（使用MD5加盐算法加密）
        if (StringUtils.isNotBlank(authUser.getPassword())) {
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        }

        // 设置用户状态为开启
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        // 设置用户删除标识为未删除
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());

        // 将用户信息插入到数据库中，并获取用户ID
        Integer count = authUserService.insert(authUser);
        Long userId = authUser.getId();

        // 创建一个普通用户角色对象，并根据条件查询对应的角色ID
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        AuthRole role = authRoleService.queryByCondition(authRole);
        Long roleId = role.getId();

        // 创建一个用户角色关联对象，设置用户ID和角色ID，并插入到数据库中
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);
        authUserRole.setRoleId(roleId);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUserRoleService.insert(authUserRole);

        // 构建角色键，将角色信息以JSON格式存入Redis中
        String roleKey = redisUtil.buildKey(authRolePrefix, authUser.getUserName());
        List<AuthRole> authRoles = new LinkedList<>();
        authRoles.add(role);
        redisUtil.set(roleKey, new Gson().toJson(authRoles));

        // 创建一个角色权限对象，设置角色ID，并查询该角色的所有权限
        AuthRolePermission authRolePermission = new AuthRolePermission();
        authRolePermission.setRoleId(roleId);
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.queryByCondition(authRolePermission);

        // 将查询到的权限ID列表转换为权限对象列表
        List<Long> permissionIdList = rolePermissionList.stream().map(AuthRolePermission::getPermissionId).collect(Collectors.toList());
        // 根据roleId查权限
        List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, authUser.getUserName());
        // 将权限信息以JSON格式存入Redis中
        redisUtil.set(permissionKey, new Gson().toJson(permissionList));
        // 返回插入的用户数量，若大于0则表示注册成功，否则表示注册失败
        return count > 0;
    }


    /**
     * 更新用户信息
     *
     * @param authUserBO 用户业务对象，包含需要更新的用户信息
     * @return 如果更新成功（即至少更新了一条记录），则返回true；否则返回false
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

    /**
     * 执行登录操作
     *
     * @param validCode 验证码
     * @return 登录成功后返回的Token信息，如果登录失败则返回null
     */
    @Override
    public SaTokenInfo doLogin(String validCode) {
        // 构建登录键，键名为"LOGIN_PREFIX"加上验证码
        String loginKey = redisUtil.buildKey(LOGIN_PREFIX, validCode);

        // 从Redis中获取与登录键对应的openId
        String openId = redisUtil.get(loginKey);

        // 如果openId为空或仅包含空白字符，则返回null
        if (StringUtils.isBlank(openId)) {
            return null;
        }

        // 创建一个AuthUserBO对象
        AuthUserBO authUserBO = new AuthUserBO();

        // 设置AuthUserBO对象的用户名属性为openId
        authUserBO.setUserName(openId);

        // 调用register方法注册用户
        this.register(authUserBO);

        // 使用StpUtil工具类进行登录操作，参数为openId
        StpUtil.login(openId);

        // 获取当前登录用户的Token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 返回Token信息
        return tokenInfo;
    }


}
