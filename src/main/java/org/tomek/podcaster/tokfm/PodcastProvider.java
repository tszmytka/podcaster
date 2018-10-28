package org.tomek.podcaster.tokfm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tomek.podcaster.tokfm.model.Podcast;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PodcastProvider {

    public Map<Integer, Podcast> getPodcasts(URL sourceUrl) {
        HashMap<Integer, Podcast> podcasts = new HashMap<>();
        try {
            Document document = Jsoup.connect(sourceUrl.toString()).get();
            Elements elements = document.select("#tok-podcasts li.tok-podcasts__podcast");
            for (Element element : elements) {
                Element divTime = element.select(".tok-podcasts__row--time").first();
                Elements spans = divTime.getElementsByTag("span");
                String[] durationElements = spans.get(0).text().split(":");
                Elements guestLinks = spans.get(1).select("a");
                String[] guests = new String[guestLinks.size()];
                int i = 0;
                for (Element guestLink : guestLinks) {
                    guests[i++] = guestLink.text();
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
            return null;
        }
        return podcasts;
    }
}
