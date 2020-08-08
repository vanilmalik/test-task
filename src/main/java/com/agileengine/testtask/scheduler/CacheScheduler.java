package com.agileengine.testtask.scheduler;

import com.agileengine.testtask.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheScheduler.class);

    @Autowired
    private CacheService cacheService;

    @Scheduled(cron = "${cron.clear.cache}")
    public void clearAllCaches() {
        LOGGER.info("Started clearing cache");
        cacheService.doClearAllCaches();
        LOGGER.info("Finished clearing cache ");
    }
}
