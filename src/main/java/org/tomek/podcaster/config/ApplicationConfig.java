package org.tomek.podcaster.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tomek.podcaster.runner.PodcastPlayerRunner;

@Configuration
public class ApplicationConfig {
    private final String mediaPlayerPath;


    public ApplicationConfig(@Value("${player.path}") String mediaPlayerPath) {
        this.mediaPlayerPath = mediaPlayerPath;
    }

    @Bean
    public PodcastPlayerRunner podcastPlayerRunner() {
        return new PodcastPlayerRunner(mediaPlayerPath);
    }
}
