package dev.tomek.podcaster.tokfm.dal;

import dev.tomek.podcaster.parser.jsoup.JsoupConnector;
import dev.tomek.podcaster.parser.jsoup.JsoupDataProvider;
import dev.tomek.podcaster.tokfm.model.Podcast;
import dev.tomek.podcaster.tokfm.podcast.DateTimeParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                String[] guests = new String[0];
                if (spans.size() > 1) {
                    Elements guestLinks = spans.get(1).select("a");
                    guests = new String[guestLinks.size()];
                    int i = 0;
                    for (Element guestLink : guestLinks) {
                        guests[i++] = guestLink.text();
                    }
                }
                String airTimeRaw = element.select(".tok-podcasts__row--audition-time").first().getElementsByTag("span").get(0).text();
                Podcast podcast = new Podcast(
                    Integer.valueOf(element.select("button.tok-podcasts__button--play").attr("data-id")),
                    element.select(".tok-podcasts__row--name a").first().text(),
                    DateTimeParser.parseDurationSeconds(spans.get(0).text()),
                    guests,
                    DateTimeParser.parseUnixTimestamp(airTimeRaw)
                );
                podcasts.put(podcast.getId(), podcast);
            }
        } catch (IOException e) {
            LOGGER.error("Cannot get podcasts", e);
        }
        return podcasts;
    }
}
