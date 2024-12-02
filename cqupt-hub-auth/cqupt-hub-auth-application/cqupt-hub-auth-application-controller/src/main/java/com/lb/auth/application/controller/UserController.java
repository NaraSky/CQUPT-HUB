package com.lb.auth.application.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    // 测试登录，浏览器访问： http://localhost:3011/user/doLogin?usern     ame=zhang&password=123456
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password) {
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对 
        if("zhang".equals(username) && "123456".equals(password)) {
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
