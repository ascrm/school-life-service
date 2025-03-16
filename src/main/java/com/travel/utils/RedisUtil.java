package com.travel.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = { "unchecked", "rawtypes", "unused" })
@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    private static final String CACHE_KEY_SEPARATOR = ":";

    /**
     * 构建缓存key
     */public String buildKey(String... strObjs) {
        return String.join(CACHE_KEY_SEPARATOR, strObjs);
    }

    /**
     * 是否存在key
     */public boolean existKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 缓存Object对象
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存Object对象
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(
            final String key,
            final T value,
            final Integer timeout,
            final TimeUnit timeUnit
    ) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public Boolean expire(
            final String key,
            final long timeout,
            final TimeUnit unit
    ) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的Object对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除单个键值对
     *
     * @param key 键值
     */
    public Boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个键值对
     *
     * @param collection 多个对象
     */
    public Long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据到末尾
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> Long setCacheListRight(final String key, final List<T> dataList) {
        return redisTemplate.opsForList().rightPushAll(key, dataList);
    }

    /**
     * 缓存List数据到开始
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> Long setCacheListLeft(final String key, final List<T> dataList) {
        return redisTemplate.opsForList().leftPushAll(key, dataList);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存 Set
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> Long setCacheSet(final String key, final Set<T> dataSet) {
        return redisTemplate.opsForSet().add(key, dataSet);
    }

    /**
     * 获得缓存的 Set
     * @param key 缓存键值
     * @return 缓存集合
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     * @param key 缓存键值
     * @param dataMap 缓存Map集合
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        redisTemplate.opsForHash().putAll(key, dataMap);
    }

    /**
     * 获得缓存的 Map
     *     * @param key 缓存键值
     * @return Map集合
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheHash(
            final String key,
            final String hKey,
            final T value
    ) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheHash(final String key, final String hKey) {
        return (T) redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key Redis键
     * @param hkey Hash键
     */
    public void delCacheHash(final String key, final String hkey) {
        redisTemplate.opsForHash().delete(key, hkey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheHash(
            final String key,
            final Collection<Object> hKeys
    ) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 使用特定规则匹配 key
     *     * @param pattern 字符串规则
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}