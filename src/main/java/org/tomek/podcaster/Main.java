package org.tomek.podcaster;

import org.tomek.podcaster.finder.TokFmFinder;
import org.tomek.podcaster.finder.tokfm.ListProvider;
import org.tomek.podcaster.runner.ExternalAppRunner;

public class Main {
    public static void main(String[] args) {
        ListProvider listProvider = new ListProvider("https://audycje.tokfm.pl/audycja/Poranek-Jacek-Zakowski/120");
        TokFmFinder finder = new TokFmFinder(listProvider.findNewestId());
        ExternalAppRunner runner = new ExternalAppRunner("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", finder.findPodcastUrl());
        runner.run();
    }
}
