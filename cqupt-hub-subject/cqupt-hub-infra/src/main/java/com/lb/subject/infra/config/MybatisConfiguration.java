package com.lb.subject.infra.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {

    // 定义一个Bean方法，用于创建并配置MybatisPlus拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        // 创建一个MybatisPlusInterceptor对象
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 向MybatisPlusInterceptor对象中添加一个内部拦截器，这里使用的是MybatisPlusAllSqlLog拦截器
        mybatisPlusInterceptor.addInnerInterceptor(new MybatisPlusAllSqlLog());
        // 返回配置好的MybatisPlusInterceptor对象
        return mybatisPlusInterceptor;
    }

}
