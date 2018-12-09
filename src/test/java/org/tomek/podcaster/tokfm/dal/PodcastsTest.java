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

        // Regular podcast with 1 guest
        int id0 = 213;
        String title0 = "Unit test podcast";
        String duration0 = "5:26";
        String[] guests0 =  {"John Doe 1"};
        rows.add(mockPodcast(String.valueOf(id0), title0, duration0, guests0));

        // Podcast with 2 guests
        int id1 = 958439;
        String title1 = "Podcast 2";
        String duration1 = "3:14";
        String[] guests1 =  {"Johny A.", "John Doe 1"};
        rows.add(mockPodcast(String.valueOf(id1), title1, duration1, guests1));

        // Podcast with no guests
        int id2 = 746489;
        String title2 = "Podcast with no guests";
        String duration2 = "11:55";
        rows.add(mockPodcast(String.valueOf(id2), title2, duration2));

        Map<Integer, Podcast> podcastsGotten = podcasts.fetchPodcasts(url);
        assertEquals(3, podcastsGotten.size());

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

        Podcast podcast2 = podcastsGotten.get(id2);
        assertEquals(id2, podcast2.getId());
        assertEquals(title2, podcast2.getTitle());
        assertEquals(11 * 60 + 55, podcast2.getDuration());
        assertEquals(0, podcast2.getGuests().length);
    }

    @Test
    void willReturnNullOnIOException() throws Exception {
        URL url = new URL("http://www.example.com");
        when(jsoupConnector.parseDocument(eq(url))).thenThrow(IOException.class);
        assertNull(podcasts.fetchPodcasts(url));
    }

    private static Element mockPodcast(String identifier, String title, String duration) {
        return mockPodcast(identifier, title, duration, new Elements());
    }

    private static Element mockPodcast(String identifier, String title, String duration, Elements spans) {
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
        when(divTime.getElementsByTag(eq("span"))).thenReturn(spans);
        Element span = mock(Element.class);
        spans.add(span);
        when(span.text()).thenReturn(duration);
        return element;
    }

    private static Element mockPodcast(String identifier, String title, String duration, String[] guests) {
        Elements spans = new Elements();
        Element element = mockPodcast(identifier, title, duration, spans);
        mockPodcastGuests(guests, spans);
        return element;
    }

    private static void mockPodcastGuests(String[] guests, Elements spans) {
        Element linksDiv = mock(Element.class);
        spans.add(linksDiv);
        Elements guestLinks = new Elements();
        when(linksDiv.select(eq("a"))).thenReturn(guestLinks);
        for (String guest : guests) {
            Element guestLink = mock(Element.class);
            guestLinks.add(guestLink);
            when(guestLink.text()).thenReturn(guest);
        }
    }
}