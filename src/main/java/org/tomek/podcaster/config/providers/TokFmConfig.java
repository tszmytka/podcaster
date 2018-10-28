package org.tomek.podcaster.config.providers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@Component
@ConfigurationProperties("providers.tokfm")
public class TokFmConfig {
    private String categoriesUrl;

    @Bean
    public CategoryProvider categoryProvider() throws MalformedURLException {
        return new CategoryProvider(new URL(categoriesUrl));
    }


    @Bean
    public PodcastProvider podcastProvider() {
        return new PodcastProvider();
    }


    public void setCategoriesUrl(String categoriesUrl) {
        this.categoriesUrl = categoriesUrl;
    }
}
