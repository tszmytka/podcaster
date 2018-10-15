package org.tomek.podcaster.finder.tokfm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ListProvider {
    private final String baseUrl;

    public ListProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String findNewestId() {
        try {
            Document document = Jsoup.connect(baseUrl).get();
            Elements buttons = document.select("#tok-podcasts button.tok-podcasts__button--play");
            if (!buttons.isEmpty()) {
                return buttons.first().attr("data-id");
            }
        } catch (IOException e) {
            //
        }

        return "";
    }
}
