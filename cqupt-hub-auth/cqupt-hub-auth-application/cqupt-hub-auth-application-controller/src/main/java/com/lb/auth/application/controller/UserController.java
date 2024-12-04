package com.lb.auth.application.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.lb.auth.application.dto.AuthUserDTO;
import com.lb.auth.application.mapper.AuthUserDTOConverter;
import com.lb.auth.common.entity.Result;
import com.lb.auth.domain.entity.AuthUserBO;
import com.lb.auth.domain.service.AuthUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private AuthUserDomainService authUserDomainService;

    /**
     * 用户注册接口
     *
     * @param authUserDTO 包含用户注册信息的DTO对象
     * @return 注册结果，包含注册是否成功的布尔值
     * @throws Exception 如果注册过程中发生异常，将捕获并处理
     */
    @RequestMapping("/register")
    public Result<Boolean> register(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isDebugEnabled()) {
                // 避免打印敏感信息，如密码
                log.debug("register authUserDTO with username={}", authUserDTO.getUserName());
            }
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名不能为空!");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getPassword()), "密码不能为空!");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getEmail()), "邮箱不能为空!");
            AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
            return Result.ok(authUserDomainService.register(authUserBO));
        } catch (Exception e) {
            log.error("register error", e);
            return Result.fail();
        }
    }


    // 测试登录，浏览器访问： http://localhost:3011/user/doLogin?username=zhang&password=123456
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password) {
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:3011/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}
