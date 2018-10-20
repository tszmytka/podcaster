package org.tomek.podcaster;

import org.springframework.stereotype.Component;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
public class Tasks {
    private final CategoryProvider categoryProvider;


    public Tasks(CategoryProvider categoryProvider) {
        this.categoryProvider = categoryProvider;
    }


    @PostConstruct
    public void getPodcasts() throws MalformedURLException {
//        Map<Integer, Category> categories = categoryProvider.getCategories();
        Category category = categoryProvider.getCategory(20);
        PodcastProvider podcastProvider = new PodcastProvider(new URL(category.getUrl()));
        Map<Integer, Podcast> podcasts = podcastProvider.getPodcasts();
        System.out.println(podcasts);
    }
}
