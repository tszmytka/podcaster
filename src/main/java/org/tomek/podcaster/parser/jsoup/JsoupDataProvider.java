package org.tomek.podcaster.parser.jsoup;

public abstract class JsoupDataProvider {
    private final JsoupConnector jsoupConnector;


    public JsoupDataProvider(JsoupConnector jsoupConnector) {
        this.jsoupConnector = jsoupConnector;
    }

    protected JsoupConnector getJsoupConnector() {
        return jsoupConnector;
    }
}
