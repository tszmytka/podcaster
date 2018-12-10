package org.tomek.podcaster.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tomek.podcaster.controller.Front;
import org.tomek.podcaster.runner.PodcastPlayerRunner;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.PodcastUrlProvider;

@Configuration
public class ApplicationConfig {
    private final CategoryProvider categoryProvider;
    private final PodcastProvider podcastProvider;
    private final PodcastUrlProvider podcastUrlProvider;
    private final String mediaPlayerPath;


    public ApplicationConfig(
        CategoryProvider categoryProvider,
        PodcastProvider podcastProvider,
        PodcastUrlProvider podcastUrlProvider,
        @Value("${player.path}") String mediaPlayerPath
    ) {
        this.categoryProvider = categoryProvider;
        this.podcastProvider = podcastProvider;
        this.podcastUrlProvider = podcastUrlProvider;
        this.mediaPlayerPath = mediaPlayerPath;
    }

    @Bean
    public Front front(PodcastPlayerRunner podcastPlayerRunner) {
        return new Front(categoryProvider, podcastProvider, podcastUrlProvider, podcastPlayerRunner);
    }

    @Bean
    public PodcastPlayerRunner podcastPlayerRunner() {
        return new PodcastPlayerRunner(mediaPlayerPath);
    }

}
