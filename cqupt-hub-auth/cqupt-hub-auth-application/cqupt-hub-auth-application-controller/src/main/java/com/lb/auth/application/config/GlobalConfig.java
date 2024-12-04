package com.lb.auth.application.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class GlobalConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 调用父类的configureMessageConverters方法
        super.configureMessageConverters(converters);
        // 向converters列表中添加自定义的MappingJackson2HttpMessageConverter
        converters.add(mappingJackson2HttpMessageConverter());
    }

    /**
     * 自定义mappingJackson2HttpMessageConverter
     * 目前实现：空值忽略，空字段可返回
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置ObjectMapper，当对象为空时不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 设置序列化时只包含非空字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 返回自定义的MappingJackson2HttpMessageConverter
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

}
