package com.river.malladmin.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.river.malladmin.common.contant.BizConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        CaffeineCache defaultCache = new CaffeineCache(BizConstants.CACHE_DEFAULT,
                Caffeine.newBuilder()
                        .initialCapacity(100)
                        .maximumSize(500)
                        .expireAfterWrite(60, TimeUnit.SECONDS)
                        .build());
        CaffeineCache userAgentCache = new CaffeineCache(BizConstants.CACHE_USERAGENT,
                Caffeine.newBuilder()
                        .initialCapacity(100)
                        .maximumSize(500)
                        .expireAfterWrite(60, TimeUnit.SECONDS)
                        .build());
        cacheManager.setCaches(Arrays.asList(defaultCache, userAgentCache));
        return cacheManager;
    }

}
