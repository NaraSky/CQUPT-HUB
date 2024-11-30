package com.lb.subject.applicaiton.config;

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
        // 向converters列表中添加MappingJackson2HttpMessageConverter
        converters.add(mappingJackson2HttpMessageConverter());
    }

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        // 创建一个ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 配置ObjectMapper，使空对象序列化时不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 配置ObjectMapper，仅序列化非空属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 使用配置好的ObjectMapper创建一个MappingJackson2HttpMessageConverter对象
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }


}
