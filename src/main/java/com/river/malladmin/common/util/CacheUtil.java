package com.river.malladmin.common.util;

import jakarta.annotation.Resource;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;


@Component
public class CacheUtil {

    @Resource
    private CacheManager cacheManager;

    /**
     * 从缓存中获取值
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @return 缓存值，如果不存在则返回 null
     */
    public Object get(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return valueWrapper.get();
            }
        }
        return null;
    }

    /**
     * 将值放入缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param value     缓存值
     */
    public void put(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    /**
     * 从缓存中移除键值对
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     */
    public void remove(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    /**
     * 检查缓存中是否包含指定键
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @return 如果存在返回 true，否则返回 false
     */
    public boolean containsKey(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            return cache.get(key) != null;
        }
        return false;
    }

    /**
     * 清空指定缓存
     *
     * @param cacheName 缓存名称
     */
    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

}