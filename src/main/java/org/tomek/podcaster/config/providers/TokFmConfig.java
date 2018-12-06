package org.tomek.podcaster.config.providers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.dal.Categories;
import org.tomek.podcaster.tokfm.dal.Podcasts;
import org.tomek.podcaster.tokfm.model.Category;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
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
    public Categories categories() throws MalformedURLException {
        return new Categories(jsoupConnector, new URL(categoriesUrl));
    }


    @Bean
    public CategoryProvider categoryProvider(Categories categories) {
        // todo Move cache setup to a separate bean and use ehcache as backend
        MutableConfiguration<Integer, Category> config = new MutableConfiguration<Integer, Category>()
            .setTypes(Integer.class, Category.class)
            .setStoreByValue(false)
            .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        return new CategoryProvider(categories, Caching.getCachingProvider().getCacheManager().createCache("jCache", config));
    }


    @Bean
    public Podcasts podcasts() {
        return new Podcasts(jsoupConnector);
    }


    @Bean
    public PodcastProvider podcastProvider(Podcasts podcasts) {
        return new PodcastProvider(podcasts);
    }


    public void setCategoriesUrl(String categoriesUrl) {
        this.categoriesUrl = categoriesUrl;
    }
}
