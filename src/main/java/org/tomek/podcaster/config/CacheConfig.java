package org.tomek.podcaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tomek.podcaster.tokfm.model.Category;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import java.net.URISyntaxException;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() throws URISyntaxException {
        return Caching.getCachingProvider().getCacheManager(getClass().getResource("/ehcache.xml").toURI(), getClass().getClassLoader());
    }


    @Bean
    public Cache<Integer, Category> categoryCache(CacheManager cacheManager) {
        return cacheManager.getCache(Category.class.getName());
    }
}
