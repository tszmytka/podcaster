package dev.tomek.podcaster.config.providers;

import dev.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import dev.tomek.podcaster.tokfm.CategoryProvider;
import dev.tomek.podcaster.tokfm.PodcastProvider;
import dev.tomek.podcaster.tokfm.PodcastUrlProvider;
import dev.tomek.podcaster.tokfm.dal.Categories;
import dev.tomek.podcaster.tokfm.dal.PodcastUrls;
import dev.tomek.podcaster.tokfm.dal.Podcasts;
import dev.tomek.podcaster.tokfm.model.Category;
import dev.tomek.podcaster.tokfm.model.Podcast;

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
