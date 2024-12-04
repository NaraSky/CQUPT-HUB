package com.lb.auth.infra.config;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
                Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class SqlStatementInterceptor implements Interceptor {

    public static final Logger log = LoggerFactory.getLogger("sys-sql");

    /**
     * 拦截方法调用，记录SQL执行时间并打印日志
     *
     * @param invocation 被拦截的方法调用
     * @return 方法调用的返回值
     * @throws Throwable 抛出方法调用过程中可能发生的异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 记录方法开始执行的时间
        long startTime = System.currentTimeMillis();
        try {
            // 继续执行被拦截的方法
            return invocation.proceed();
        } finally {
            // 计算方法执行的时间
            long timeConsuming = System.currentTimeMillis() - startTime;
            // 打印SQL执行时间日志
            log.info("执行SQL:{}ms", timeConsuming);
            // 判断SQL执行时间，并打印不同时间段的日志
            if (timeConsuming > 999 && timeConsuming < 5000) {
                // SQL执行时间大于1秒且小于5秒
                log.info("执行SQL大于1s:{}ms", timeConsuming);
            } else if (timeConsuming >= 5000 && timeConsuming < 10000) {
                // SQL执行时间大于等于5秒且小于10秒
                log.info("执行SQL大于5s:{}ms", timeConsuming);
            } else if (timeConsuming >= 10000) {
                // SQL执行时间大于等于10秒
                log.info("执行SQL大于10s:{}ms", timeConsuming);
            }
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
