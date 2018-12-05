package org.tomek.podcaster.tokfm.dal;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tomek.podcaster.parser.jsoup.JsoupConnector;
import org.tomek.podcaster.parser.jsoup.JsoupDataProvider;
import org.tomek.podcaster.tokfm.model.Category;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Categories extends JsoupDataProvider {

    private final URL url;

    public Categories(JsoupConnector jsoupConnector, URL url) {
        super(jsoupConnector);
        this.url = url;
    }


    public Map<Integer, Category> fetchCategories() {
        HashMap<Integer, Category> categories = new HashMap<>();
        try {
            Elements elements = getJsoupConnector().parseDocument(url).select("#tok_audycje_list li.tok_audycje__element");
            for (Element element : elements) {
                Elements link = element.select(".tok_audycje__prowadzacy");
                Elements authorLinks = element.select("p.tok_audycje__leader");
                String[] authors = new String[authorLinks.size()];
                int i = 0;
                for (Element authorLink : authorLinks) {
                    authors[i++] = authorLink.text();
                }
                Category category = new Category(
                    Integer.valueOf(element.getElementsByClass("tok-podcasts__button").first().attr("data-subscribe_id")),
                    link.select(".tok_audycje__name").first().text(),
                    authors,
                    link.attr("href"),
                    link.first().getElementsByTag("img").first().attr("src")
                );
                categories.put(category.getId(), category);
            }
        } catch (IOException e) {
            return null;
        }

        return categories;
    }
}
