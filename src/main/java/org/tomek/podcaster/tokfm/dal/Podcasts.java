package org.tomek.podcaster.tokfm.dal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.parser.jsoup.JsoupDataProvider;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Podcasts extends JsoupDataProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(Podcasts.class);

    public Podcasts(JsoupConnector jsoupConnector) {
        super(jsoupConnector);
    }


    public Map<Integer, Podcast> fetchPodcasts(URL sourceUrl) {
        HashMap<Integer, Podcast> podcasts = new HashMap<>();
        try {
            Elements elements = getJsoupConnector().parseDocument(sourceUrl).select("#tok-podcasts li.tok-podcasts__podcast");
            for (Element element : elements) {
                Element divTime = element.select(".tok-podcasts__row--time").first();
                Elements spans = divTime.getElementsByTag("span");
                String[] durationElements = spans.get(0).text().split(":");
                String[] guests = new String[0];
                if (spans.size() > 1) {
                    Elements guestLinks = spans.get(1).select("a");
                    guests = new String[guestLinks.size()];
                    int i = 0;
                    for (Element guestLink : guestLinks) {
                        guests[i++] = guestLink.text();
                    }
                }
                int duration = Integer.valueOf(durationElements[0]) * 60 + Integer.valueOf(durationElements[1]);
                Podcast podcast = new Podcast(
                    Integer.valueOf(element.select("button.tok-podcasts__button--play").attr("data-id")),
                    element.select(".tok-podcasts__row--name a").first().text(),
                    duration,
                    guests
                );
                podcasts.put(podcast.getId(), podcast);
            }
        } catch (IOException e) {
            LOGGER.error("Cannot get podcasts", e);
        }
        return podcasts;
    }
}
