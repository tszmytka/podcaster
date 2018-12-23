package org.tomek.podcaster.config.providers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.PodcastUrlProvider;
import org.tomek.podcaster.tokfm.dal.Categories;
import org.tomek.podcaster.tokfm.dal.PodcastUrls;
import org.tomek.podcaster.tokfm.dal.Podcasts;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import javax.cache.Cache;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Configuration
@Component
@ConfigurationProperties("providers.tokfm")
public class TokFmConfig {
    private String categoriesUrl;
    private String podcastsUrl;

    private final JsoupConnector jsoupConnector;


    public TokFmConfig(JsoupConnector jsoupConnector) {
        this.jsoupConnector = jsoupConnector;
    }

    @Bean
    public Categories categories() throws MalformedURLException {
        return new Categories(jsoupConnector, new URL(categoriesUrl));
    }

    @Bean
    public CategoryProvider categoryProvider(Categories categories, Cache<Integer, Category> categoryCache) {
        return new CategoryProvider(categories, categoryCache);
    }

    @Bean
    public Podcasts podcasts() {
        return new Podcasts(jsoupConnector);
    }

    @Bean
    public PodcastProvider podcastProvider(Podcasts podcasts, Cache<String, Map<Integer, Podcast>> podcastCache) {
        return new PodcastProvider(podcasts, podcastCache);
    }

    @Bean
    public PodcastUrls podcastUrls() throws MalformedURLException {
        return new PodcastUrls(new URL(podcastsUrl));
    }

    @Bean
    public PodcastUrlProvider podcastUrlProvider(PodcastUrls podcastUrls) {
        return new PodcastUrlProvider(podcastUrls);
    }

    public void setCategoriesUrl(String categoriesUrl) {
        this.categoriesUrl = categoriesUrl;
    }

    public void setPodcastsUrl(String podcastsUrl) {
        this.podcastsUrl = podcastsUrl;
    }
}
