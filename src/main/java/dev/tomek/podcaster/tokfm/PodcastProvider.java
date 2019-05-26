package dev.tomek.podcaster.tokfm;

import dev.tomek.podcaster.tokfm.model.Podcast;
import dev.tomek.podcaster.tokfm.dal.Podcasts;

import javax.cache.Cache;
import java.net.URL;
import java.util.Map;

public class PodcastProvider {

    private final Podcasts podcasts;

    private final Cache<String, Map<Integer, Podcast>> cache;

    public PodcastProvider(Podcasts podcasts, Cache<String, Map<Integer, Podcast>> cache) {
        this.podcasts = podcasts;
        this.cache = cache;
    }

    public Map<Integer, Podcast> getPodcasts(URL sourceUrl) {
        String cacheKey = sourceUrl.toString();
        Map<Integer, Podcast> cachedPodcasts = cache.get(cacheKey);
        if (cachedPodcasts == null) {
            cachedPodcasts = podcasts.fetchPodcasts(sourceUrl);
            cache.put(cacheKey, cachedPodcasts);
        }
        return cachedPodcasts;
    }
}
