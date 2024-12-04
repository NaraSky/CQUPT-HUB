package com.lb.gateway.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.gateway.entity.Result;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 处理异常并返回响应的方法
     *
     * @param exchange 交换对象，包含请求和响应信息
     * @param ex       抛出的异常对象
     * @return Mono<Void> 返回Mono对象，表示异步操作的完成
     */
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 获取请求和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 初始化错误码和错误信息
        Integer code = 200;
        String message = "";

        // 判断异常类型
        if (ex instanceof SaTokenException) {
            // 如果是权限异常，设置错误码为401，错误信息为用户无权限
            code = 401;
            message = "用户无权限";
            // 打印异常堆栈信息
            ex.printStackTrace();
        } else {
            // 如果是其他异常，设置错误码为500，错误信息为系统繁忙
            code = 500;
            message = "系统繁忙";
            // 打印异常堆栈信息
            ex.printStackTrace();
        }

        // 构造返回结果
        Result result = Result.fail(code, message);

        // 设置响应的内容类型为JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 返回响应体
        return response.writeWith(Mono.fromSupplier(() -> {
            // 获取DataBufferFactory对象
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            byte[] bytes = null;

            try {
                // 将结果对象转换为字节数组
                bytes = objectMapper.writeValueAsBytes(result);
            } catch (JsonProcessingException e) {
                // 捕获Json处理异常并打印堆栈信息
                e.printStackTrace();
            }

            // 将字节数组包装成DataBuffer并返回
            return dataBufferFactory.wrap(bytes);
        }));
    }

}
