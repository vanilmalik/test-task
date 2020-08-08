package com.agileengine.testtask.service.impl;

import com.agileengine.testtask.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void doClearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
