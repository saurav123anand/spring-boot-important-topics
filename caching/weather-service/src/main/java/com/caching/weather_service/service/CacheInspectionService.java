package com.caching.weather_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheInspectionService {
    @Autowired
    private CacheManager cacheManager;

    public void printCacheContents(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache!=null){
            System.out.println("cache contents");
            System.out.println(Objects.requireNonNull(cache.getNativeCache()));
        }
        else System.out.println("cache not found with name: "+cacheName);
    }
}
