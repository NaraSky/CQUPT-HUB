package com.lb.subject.infra.config;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;


import org.apache.ibatis.session.RowBounds;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
                Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class SqlStatementInterceptor implements Interceptor {

    // 初始化日志记录器
    public static final Logger log = LoggerFactory.getLogger("sys-sql");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 记录SQL执行开始时间
        long startTime = System.currentTimeMillis();
        try {
            // 执行SQL操作
            return invocation.proceed();
        } finally {
            // 计算SQL执行时间
            long timeConsuming = System.currentTimeMillis() - startTime;
            // 记录SQL执行时间
            log.info("执行SQL:{}ms", timeConsuming);
            // 判断SQL执行时间并进行不同级别的日志记录
            if (timeConsuming > 999 && timeConsuming < 5000) {
                log.info("执行SQL大于1s:{}ms", timeConsuming);
            } else if (timeConsuming >= 5000 && timeConsuming < 10000) {
                log.info("执行SQL大于5s:{}ms", timeConsuming);
            } else if (timeConsuming >= 10000) {
                log.info("执行SQL大于10s:{}ms", timeConsuming);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        // 包装目标对象，应用拦截器
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 设置拦截器属性（当前实现为空）
    }
}
