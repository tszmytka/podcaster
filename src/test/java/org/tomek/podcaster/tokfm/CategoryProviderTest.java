package org.tomek.podcaster.tokfm;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.tokfm.model.Category;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryProviderTest {
    private CategoryProvider categoryProvider;

    @Mock
    private JsoupConnector jsoupConnector;

    private URL url;


    @BeforeEach
    void setUp() throws MalformedURLException {
        MockitoAnnotations.initMocks(this);
        url = new URL("http://www.example.com");
        categoryProvider = new CategoryProvider(jsoupConnector, url);
    }


    @Test
    void canGetPodcasts() throws Exception {
        Document document = Mockito.mock(Document.class);
        when(jsoupConnector.parseDocument(eq(url))).thenReturn(document);

        Elements rows = new Elements();
        when(document.select(eq("#tok_audycje_list li.tok_audycje__element"))).thenReturn(rows);

        int id0 = 13245;
        String author0 = "Author1";
        String categoryName0 = "Unit test name 123";
        String categoryUrl0 = "www.my.category.com/path";
        String picUrl0 = "www.host.com/path/to/img.png";
        rows.add(mockCategory(String.valueOf(id0), new String[]{author0}, categoryName0, categoryUrl0, picUrl0));

        int id1 = 86746;
        String[] authors1 = {"Jon Doe", "Your Mother"};
        String categoryName1 = "Test Category 3";
        String categoryUrl1 = "www.my.category.com";
        String picUrl1 = "/img.png";
        rows.add(mockCategory(String.valueOf(id1), authors1, categoryName1, categoryUrl1, picUrl1));

        Map<Integer, Category> categories = categoryProvider.getCategories();
        assertEquals(2, categories.size());
        Category category0 = categories.get(id0);
        assertEquals(author0, category0.getAuthors()[0]);
        assertEquals(categoryName0, category0.getName());
        assertEquals(categoryUrl0, category0.getUrl());
        assertEquals(picUrl0, category0.getPicHref());

        Category category1 = categories.get(id1);
        // disregards order
        assertThat(Arrays.asList(category1.getAuthors()), containsInAnyOrder(authors1));
        // also checks order
        assertArrayEquals(authors1, category1.getAuthors());
        assertEquals(categoryName1, category1.getName());
        assertEquals(categoryUrl1, category1.getUrl());
        assertEquals(picUrl1, category1.getPicHref());
    }


    private static Element mockCategory(String identifier, String[] authors, String categoryName, String categoryUrl, String picUrl) {
        Element element = mock(Element.class);
        Elements link = mock(Elements.class);
        when(element.select(eq(".tok_audycje__prowadzacy"))).thenReturn(link);

        // build authors
        Elements authorLinks = new Elements();
        when(element.select(eq("p.tok_audycje__leader"))).thenReturn(authorLinks);
        for (String author : authors) {
            Element authorLink = mock(Element.class);
            authorLinks.add(authorLink);
            when(authorLink.text()).thenReturn(author);
        }

        // build identifier
        Elements buttons = new Elements();
        when(element.getElementsByClass(eq("tok-podcasts__button"))).thenReturn(buttons);
        Element buttonSubscribe = mock(Element.class);
        buttons.add(buttonSubscribe);
        when(buttonSubscribe.attr(eq("data-subscribe_id"))).thenReturn(identifier);

        // build name
        Elements spans = new Elements();
        when(link.select(eq(".tok_audycje__name"))).thenReturn(spans);
        Element nameSpan = mock(Element.class);
        spans.add(nameSpan);
        when(nameSpan.text()).thenReturn(categoryName);

        // build url
        when(link.attr("href")).thenReturn(categoryUrl);

        // build picture url
        Element firstLink = mock(Element.class);
        when(link.first()).thenReturn(firstLink);
        Elements imgs = new Elements();
        when(firstLink.getElementsByTag(eq("img"))).thenReturn(imgs);
        Element img = mock(Element.class);
        imgs.add(img);
        when(img.attr(eq("src"))).thenReturn(picUrl);

        return element;
    }
}