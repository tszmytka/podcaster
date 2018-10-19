package org.tomek.podcaster;

import org.tomek.podcaster.tokfm.CategoriesProvider;
import org.tomek.podcaster.tokfm.model.Category;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        CategoriesProvider categoriesProvider = new CategoriesProvider(new URL("https://audycje.tokfm.pl/audycje-tokfm"));
        Map<Integer, Category> categories = categoriesProvider.getCategories();
        System.out.println(categories.toString());


//        ListProvider listProvider = new ListProvider("https://audycje.tokfm.pl/audycja/Poranek-Jacek-Zakowski/120");
//        TokFmFinder finder = new TokFmFinder(listProvider.findNewestId());
//        ExternalAppRunner runner = new ExternalAppRunner("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", finder.findPodcastUrl());
//        runner.run();
    }
}
