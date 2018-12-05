package org.tomek.podcaster.tokfm;

import org.tomek.podcaster.tokfm.dal.Podcasts;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.net.URL;
import java.util.Map;

public class PodcastProvider {

    private final Podcasts podcasts;

    public PodcastProvider(Podcasts podcasts) {
        this.podcasts = podcasts;
    }

    public Map<Integer, Podcast> getPodcasts(URL sourceUrl) {
        return podcasts.fetchPodcasts(sourceUrl);
    }
}
