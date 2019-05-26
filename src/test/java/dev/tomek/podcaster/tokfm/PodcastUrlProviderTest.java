package dev.tomek.podcaster.tokfm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import dev.tomek.podcaster.tokfm.dal.PodcastUrls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PodcastUrlProviderTest {
    private PodcastUrlProvider podcastUrlProvider;

    @Mock
    private PodcastUrls podcastUrls;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        podcastUrlProvider = new PodcastUrlProvider(podcastUrls);
    }

    @Test
    void canGetPodcastUrl() {
        String podcastId = "podcast123";
        String podcastUrl = "http://www.example.org/podcast/podcast123";
        when(podcastUrls.fetchPodcastUrl(eq(podcastId))).thenReturn(podcastUrl);
        assertEquals(podcastUrl, podcastUrlProvider.getPodcastUrl(podcastId));
        verify(podcastUrls).fetchPodcastUrl(eq(podcastId));
    }
}