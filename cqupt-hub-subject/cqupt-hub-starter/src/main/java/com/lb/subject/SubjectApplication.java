package com.lb.subject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.lb")
@SpringBootApplication
public class SubjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);  // 启动应用
    }
}
