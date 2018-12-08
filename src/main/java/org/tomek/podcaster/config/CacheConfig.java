package org.tomek.podcaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
public class CacheConfig {

    private final String dir = "data/cache";


    @Bean
    public CacheManager cacheManager() {
        // todo this works but how to configure max disk space used?
//        EhcacheCachingProvider ehCacheProvider = (EhcacheCachingProvider) Caching.getCachingProvider();
//        DefaultConfiguration ehCacheConfig = new DefaultConfiguration(ehCacheProvider.getDefaultClassLoader(), new DefaultPersistenceConfiguration(new File(dir)));
//        ehCacheProvider.getCacheManager(ehCacheProvider.getDefaultURI(), ehCacheConfig);
//        return ehCacheProvider.getCacheManager(ehCacheProvider.getDefaultURI(), ehCacheConfig);

        return Caching.getCachingProvider().getCacheManager();
    }
}
