package org.tomek.podcaster.tokfm;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

class CategoryProviderTest {
    PodcastProvider podcastProvider;

    @BeforeEach
    void setUp() {
        podcastProvider = new PodcastProvider();
    }

    @Ignore
    @Test
    void canGetPodcasts() throws Exception {
        podcastProvider.getPodcasts(new URL("www.example.com"));

    }
}