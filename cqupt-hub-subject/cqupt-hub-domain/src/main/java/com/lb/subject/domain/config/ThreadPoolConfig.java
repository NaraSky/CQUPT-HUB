package com.lb.subject.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    /**
     * 获取标签处理线程池
     *
     * @return 一个配置好的ThreadPoolExecutor实例
     */
    @Bean(name = "labelThreadPool")
    public ThreadPoolExecutor getLabelThreadPool() {
        // 创建一个ThreadPoolExecutor实例
        // 核心线程数为20
        // 最大线程数为100
        // 线程空闲时间为5秒
        // 时间单位为秒
        // 使用LinkedBlockingDeque作为工作队列，容量为40
        // 使用默认的线程工厂
        // 当工作队列已满时，新的任务将由调用线程处理
        return new ThreadPoolExecutor(20, 100, 5,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(40), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }


}
