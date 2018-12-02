package org.tomek.podcaster.config.providers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@Component
@ConfigurationProperties("providers.tokfm")
public class TokFmConfig {
    private String categoriesUrl;

    private final JsoupConnector jsoupConnector;


    public TokFmConfig(JsoupConnector jsoupConnector) {
        this.jsoupConnector = jsoupConnector;
    }


    @Bean
    public CategoryProvider categoryProvider() throws MalformedURLException {
        return new CategoryProvider(jsoupConnector, new URL(categoriesUrl));
    }


    @Bean
    public PodcastProvider podcastProvider() {
        return new PodcastProvider(jsoupConnector);
    }


    public void setCategoriesUrl(String categoriesUrl) {
        this.categoriesUrl = categoriesUrl;
    }
}
