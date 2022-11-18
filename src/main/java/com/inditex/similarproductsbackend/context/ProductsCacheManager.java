package com.inditex.similarproductsbackend.context;

import com.google.common.cache.CacheBuilder;
import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Data
public class ProductsCacheManager extends ConcurrentMapCacheManager {
    private Long cacheTimeout;
    private Long maximumSize;

    @Override
    protected Cache createConcurrentMapCache(final String name) {
        ConcurrentMap map = CacheBuilder.newBuilder()
                .expireAfterWrite(cacheTimeout, TimeUnit.MINUTES).build().asMap();
        return new ConcurrentMapCache(name, map, false);
    }
}