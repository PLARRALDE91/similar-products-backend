package com.inditex.similarproductsbackend.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${cache.timeout}")
    private long cacheTimeout;

    @Value("${cache.name}")
    private String cacheName;

    @Bean
    public CacheManager cacheManager() {
        ProductsCacheManager manager = new ProductsCacheManager();
        manager.setCacheTimeout(cacheTimeout);
        manager.createConcurrentMapCache(cacheName);
        return manager;
    }
}
