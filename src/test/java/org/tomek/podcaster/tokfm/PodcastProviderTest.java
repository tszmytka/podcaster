package org.tomek.podcaster.tokfm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tomek.podcaster.tokfm.dal.Podcasts;
import org.tomek.podcaster.tokfm.model.Podcast;

import javax.cache.Cache;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PodcastProviderTest {
    private PodcastProvider podcastProvider;

    @Mock
    private Podcasts podcasts;

    @Mock
    private Cache<String, Map<Integer, Podcast>> cache;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        podcastProvider = new PodcastProvider(podcasts, cache);
    }

    @Test
    void canGetPodcastsWithColdCache() throws Exception {
        URL url = new URL("http://www.example.com/path/to/podcast-group/1");
        Map<Integer, Podcast> podcastsFetched = new HashMap<>();
        podcastsFetched.put(1, new Podcast(1, "podcast 1", 134, new String[]{"Guest1"}));
        when(podcasts.fetchPodcasts(eq(url))).thenReturn(podcastsFetched);
        assertEquals(podcastsFetched, podcastProvider.getPodcasts(url));
        verify(podcasts).fetchPodcasts(url);
        verify(cache).put(anyString(), anyMap());
    }

    @Test
    void canGetPodcastsWithWarmCache() throws Exception {
        URL url = new URL("http://www.example.com/path/to/podcast-group/1");
        Map<Integer, Podcast> podcastsCached = new HashMap<>();
        podcastsCached.put(1, new Podcast(1, "podcast 1", 134, new String[]{"Guest1"}));
        podcastsCached.put(2, new Podcast(2, "podcast 2", 4, new String[]{"NoBody"}));
        when(cache.get(url.toString())).thenReturn(podcastsCached);
        assertEquals(podcastsCached, podcastProvider.getPodcasts(url));
        verify(podcasts, never()).fetchPodcasts(url);
        verify(cache, never()).put(anyString(), anyMap());
    }
}