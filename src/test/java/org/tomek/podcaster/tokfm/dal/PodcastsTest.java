package org.tomek.podcaster.tokfm.dal;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class PodcastsTest {

    private Podcasts podcasts;

    @Mock
    private JsoupConnector jsoupConnector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        podcasts = new Podcasts(jsoupConnector);
    }


    @Test
    void canFetchPodcasts() throws Exception {
        Document document = Mockito.mock(Document.class);
        URL url = new URL("http://www.example.com");
        when(jsoupConnector.parseDocument(eq(url))).thenReturn(document);

        Elements rows = new Elements();
        when(document.select(eq("#tok-podcasts li.tok-podcasts__podcast"))).thenReturn(rows);


        Map<Integer, Podcast> podcastsGotten = podcasts.fetchPodcasts(url);
        assertEquals(0, podcastsGotten.size());
    }


    @Test
    void willReturnNullOnIOException() throws Exception {
        URL url = new URL("http://www.example.com");
        when(jsoupConnector.parseDocument(eq(url))).thenThrow(IOException.class);
        assertNull(podcasts.fetchPodcasts(url));
    }
}