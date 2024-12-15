package com.lb.wx.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ZhiYu
 * @date 2024/12/4 00:11
 * @description redis配置类
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 创建String类型的序列化器
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        // 设置key的序列化器
        redisTemplate.setKeySerializer(redisSerializer);
        // 设置value的序列化器
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        // 设置hash key的序列化器
        redisTemplate.setHashKeySerializer(redisSerializer);
        // 设置hash value的序列化器
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());

        return redisTemplate;
    }

    // 创建一个Jackson2JsonRedisSerializer实例
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        // 创建Jackson2JsonRedisSerializer对象
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置属性访问器为所有，并检测所有可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 配置反序列化时忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 启用默认类型检测，用于非最终类，并将类型信息作为属性添加
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 设置ObjectMapper到Jackson2JsonRedisSerializer
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}
