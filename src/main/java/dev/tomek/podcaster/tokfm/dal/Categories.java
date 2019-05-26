package dev.tomek.podcaster.tokfm.dal;

import dev.tomek.podcaster.parser.jsoup.JsoupConnector;
import dev.tomek.podcaster.parser.jsoup.JsoupDataProvider;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import dev.tomek.podcaster.tokfm.model.Category;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Categories extends JsoupDataProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(Categories.class);

    private final URL url;

    public Categories(JsoupConnector jsoupConnector, URL url) {
        super(jsoupConnector);
        this.url = url;
    }


    public Map<Integer, Category> fetchCategories() {
        StopWatch stopWatch = new StopWatch("Fetching categories");
        stopWatch.start();
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
                Elements podcastButton = element.getElementsByClass("tok-podcasts__button");
                int categoryId = -1;
                if (!podcastButton.isEmpty()) {
                    categoryId = Integer.valueOf(podcastButton.first().attr("data-subscribe_id"));
                }
                if (categoryId == -1) {
                    String[] linkElements = link.attr("href").split("/");
                    categoryId = Integer.valueOf(linkElements[linkElements.length - 1]);
                }
                Category category = new Category(
                    categoryId,
                    link.select(".tok_audycje__name").first().text(),
                    authors,
                    link.attr("href"),
                    link.first().getElementsByTag("img").first().attr("src")
                );
                categories.put(category.getId(), category);
            }
        } catch (IOException e) {
            LOGGER.error("Cannot get categories", e);
        }
        stopWatch.stop();
        LOGGER.info(stopWatch.toString());
        return categories;
    }
}
