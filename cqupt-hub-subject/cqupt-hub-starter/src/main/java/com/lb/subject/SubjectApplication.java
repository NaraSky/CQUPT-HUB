package com.lb.subject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Subject 微服务启动类
 */
@MapperScan("com.lb.**.mapper")
@ComponentScan("com.lb")
@SpringBootApplication
public class SubjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);  // 启动应用
    }
}
