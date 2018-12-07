package org.tomek.podcaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tomek.podcaster.controller.Front;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
public class ApplicationConfig {
    private final CategoryProvider categoryProvider;
    private final PodcastProvider podcastProvider;


    public ApplicationConfig(CategoryProvider categoryProvider, PodcastProvider podcastProvider) {
        this.categoryProvider = categoryProvider;
        this.podcastProvider = podcastProvider;
    }


    @Bean
    public Front front() {
        return new Front(categoryProvider, podcastProvider);
    }
}
