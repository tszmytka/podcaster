package org.tomek.podcaster.tokfm.dal;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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

        int id0 = 213;
        String title0 = "Unit test podcast";
        String duration0 = "5:26";
        String[] guests0 =  {"John Doe 1"};
        rows.add(mockPodcast(String.valueOf(id0), title0, duration0, guests0));

        int id1 = 958439;
        String title1 = "Podcast 2";
        String duration1 = "3:14";
        String[] guests1 =  {"Johny A.", "John Doe 1"};
        rows.add(mockPodcast(String.valueOf(id1), title1, duration1, guests1));

        Map<Integer, Podcast> podcastsGotten = podcasts.fetchPodcasts(url);
        assertEquals(2, podcastsGotten.size());

        Podcast podcast0 = podcastsGotten.get(id0);
        assertEquals(id0, podcast0.getId());
        assertEquals(title0, podcast0.getTitle());
        assertEquals(5 * 60 + 26, podcast0.getDuration());
        assertArrayEquals(guests0, podcast0.getGuests());

        Podcast podcast1 = podcastsGotten.get(id1);
        assertEquals(id1, podcast1.getId());
        assertEquals(title1, podcast1.getTitle());
        assertEquals(3 * 60 + 14, podcast1.getDuration());
        assertArrayEquals(guests1, podcast1.getGuests());
    }


    @Test
    void willReturnNullOnIOException() throws Exception {
        URL url = new URL("http://www.example.com");
        when(jsoupConnector.parseDocument(eq(url))).thenThrow(IOException.class);
        assertNull(podcasts.fetchPodcasts(url));
    }


    private static Element mockPodcast(String identifier, String title, String duration, String[] guests) {
        Element element = mock(Element.class);

        // build identifier
        Elements buttonPlay = mock(Elements.class);
        when(element.select(eq("button.tok-podcasts__button--play"))).thenReturn(buttonPlay);
        when(buttonPlay.attr(eq("data-id"))).thenReturn(identifier);

        // build title
        Elements titles = mock(Elements.class);
        when(element.select(eq(".tok-podcasts__row--name a"))).thenReturn(titles);
        Element titleElement = mock(Element.class);
        when(titles.first()).thenReturn(titleElement);
        when(titleElement.text()).thenReturn(title);

        // build duration
        Elements divsTime = mock(Elements.class);
        when(element.select(eq(".tok-podcasts__row--time"))).thenReturn(divsTime);
        Element divTime = mock(Element.class);
        when(divsTime.first()).thenReturn(divTime);
        Elements spans = mock(Elements.class);
        when(divTime.getElementsByTag(eq("span"))).thenReturn(spans);
        Element span = mock(Element.class);
        when(spans.get(eq(0))).thenReturn(span);
        when(span.text()).thenReturn(duration);

        // build guests
        Element linksDiv = mock(Element.class);
        when(spans.get(eq(1))).thenReturn(linksDiv);
        Elements guestLinks = new Elements();
        when(linksDiv.select(eq("a"))).thenReturn(guestLinks);
        for (String guest : guests) {
            Element guestLink = mock(Element.class);
            guestLinks.add(guestLink);
            when(guestLink.text()).thenReturn(guest);
        }

        return element;
    }
}