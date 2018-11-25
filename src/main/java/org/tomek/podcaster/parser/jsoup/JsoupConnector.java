package org.tomek.podcaster.parser.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * Application internal wrapper for interacting with Jsoup library.
 */
public class JsoupConnector {
    public Document parseDocument(URL url) throws IOException {
        return Jsoup.connect(url.toString()).get();
    }
}
