package org.tomek.podcaster;

import org.tomek.podcaster.finder.TokFmFinder;
import org.tomek.podcaster.runner.ExternalAppRunner;

public class Main {
    public static void main(String[] args) {
        TokFmFinder finder = new TokFmFinder("67938");
        ExternalAppRunner runner = new ExternalAppRunner("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe", finder.findPodcastUrl());
        runner.run();
    }
}
