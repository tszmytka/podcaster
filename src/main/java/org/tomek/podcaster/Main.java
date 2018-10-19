package org.tomek.podcaster;

import org.tomek.podcaster.finder.TokFmFinder;
import org.tomek.podcaster.finder.tokfm.ListProvider;
import org.tomek.podcaster.runner.ExternalAppRunner;
import org.tomek.podcaster.tokfm.CategoryProvider;
import org.tomek.podcaster.tokfm.PodcastProvider;
import org.tomek.podcaster.tokfm.model.Category;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
//        getPodcasts();
        ListProvider listProvider = new ListProvider("https://audycje.tokfm.pl/audycja/Poranek-Jacek-Zakowski/120");
        TokFmFinder finder = new TokFmFinder(listProvider.findNewestId());
        ExternalAppRunner runner = new ExternalAppRunner("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", finder.findPodcastUrl());
        runner.run();
    }

    private static void getPodcasts() throws MalformedURLException {
        CategoryProvider categoryProvider = new CategoryProvider(new URL("https://audycje.tokfm.pl/audycje-tokfm"));
//        Map<Integer, Category> categories = categoryProvider.getCategories();
        Category category = categoryProvider.getCategory(20);
        PodcastProvider podcastProvider = new PodcastProvider(new URL(category.getUrl()));
        Map<Integer, Podcast> podcasts = podcastProvider.getPodcasts();
        System.out.println(podcasts);
    }
}
