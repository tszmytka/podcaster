package org.tomek.podcaster;

import org.tomek.podcaster.finder.TokFmFinder;
import org.tomek.podcaster.finder.tokfm.ListProvider;
import org.tomek.podcaster.runner.PodcastPlayerRunner;

/**
 * Legacy entry point - can be used till Spring Boot one is not finished
 */
public class Main {
    public static void main(String[] args) {
        ListProvider listProvider = new ListProvider("https://audycje.tokfm.pl/audycja/Poranek-Jacek-Zakowski/120");
        TokFmFinder finder = new TokFmFinder(listProvider.findNewestId());
//        PodcastPlayerRunner runner = new PodcastPlayerRunner("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", finder.findPodcastUrl());
//        runner.run();
    }
}
