package dev.tomek.podcaster.config;

import dev.tomek.podcaster.runner.PodcastPlayerRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
