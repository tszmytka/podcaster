package org.tomek.podcaster.tokfm;

import org.tomek.podcaster.tokfm.dal.PodcastUrls;

public class PodcastUrlProvider {
    private final PodcastUrls podcastUrls;

    public PodcastUrlProvider(PodcastUrls podcastUrls) {
        this.podcastUrls = podcastUrls;
    }

    public String getPodcastUrl(String podcastId) {
        return podcastUrls.fetchPodcastUrl(podcastId);
    }
}
