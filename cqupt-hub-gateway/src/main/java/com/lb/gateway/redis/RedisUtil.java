package com.lb.gateway.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate redisTemplate;

    private static final String CACHE_KEY_SEPARATOR = ".";

    /**
     * 构建缓存key
     *
     * @param strObjs 要拼接的字符串数组
     * @return 拼接后的缓存key
     */
    public String buildKey(String... strObjs) {
        return String.join(CACHE_KEY_SEPARATOR, strObjs);
    }

    /**
     * 判断指定的键是否存在
     *
     * @param key 要检查的键
     * @return 如果键存在，则返回true；否则返回false
     */
    public boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除指定的键
     *
     * @param key 要删除的键
     * @return 如果删除成功，则返回true；否则返回false
     */
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 设置键值对（不带过期时间）
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置键值对（带过期时间）
     *
     * 如果键不存在，则设置键值对，并设置过期时间；如果键已存在，则不做任何操作。
     *
     * @param key       键
     * @param value     值
     * @param time      过期时间
     * @param timeUnit 时间单位
     * @return 如果键不存在并成功设置，则返回true；如果键已存在或设置失败，则返回false
     */
    public boolean setNx(String key, String value, Long time, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit));
    }

    /**
     * 获取string类型缓存
     *
     * 根据指定的键从Redis中获取对应的字符串值。
     *
     * @param key 要获取的缓存键
     * @return 缓存对应的字符串值，如果不存在则返回null
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 向有序集合中添加一个或多个成员，或者更新已存在成员的分数。
     *
     * @param key 有序集合的键
     * @param value 要添加到有序集合的成员
     * @param score 成员的分数，分数越小，排名越靠前
     * @return 如果操作成功返回true，如果成员已经存在返回false
     */
    public Boolean zAdd(String key, String value, Long score) {
        return redisTemplate.opsForZSet().add(key, value, Double.valueOf(String.valueOf(score)));
    }

    /**
     * 获取有序集合中的成员数量
     *
     * @param key 有序集合的键
     * @return 有序集合中的成员数量
     */
    public Long countZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取有序集合中指定范围内的成员
     *
     * @param key 有序集合的键
     * @param start 起始索引（包含）
     * @param end 结束索引（包含）
     * @return 返回指定范围内的成员集合
     */
    public Set<String> rangeZset(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 从有序集合中移除指定的成员
     *
     * @param key   有序集合的键
     * @param value 要移除的成员
     * @return 被移除的成员数量
     */
    public Long removeZset(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 从有序集合中移除一组指定的成员
     *
     * @param key   有序集合的键
     * @param value 要移除的成员集合
     */
    public void removeZsetList(String key, Set<String> value) {
        value.stream().forEach((val) -> redisTemplate.opsForZSet().remove(key, val));
    }

    /**
     * 获取有序集合中指定成员的分数
     *
     * @param key   有序集合的键
     * @param value 要获取分数的成员
     * @return 返回指定成员的分数，如果成员不存在则返回null
     */
    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 根据分数范围获取有序集合中的成员
     *
     * @param key   有序集合的键
     * @param start 分数范围的起始值（包含）
     * @param end   分数范围的结束值（包含）
     * @return 返回分数在指定范围内的成员集合
     */
    public Set<String> rangeByScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, Double.parseDouble(String.valueOf(start)), Double.parseDouble(String.valueOf(end)));
    }

    /**
     * 为有序集合中的成员增加分数
     *
     * @param key   有序集合的键
     * @param obj   要增加分数的成员
     * @param score 要增加的分数
     * @return 增加分数后的新分数
     */
    public Object addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }

    /**
     * 获取有序集合中成员的排名
     *
     * @param key 有序集合的键
     * @param obj 要获取排名的成员
     * @return 返回成员的排名（从0开始），如果成员不存在则返回null
     */
    public Object rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }
}
