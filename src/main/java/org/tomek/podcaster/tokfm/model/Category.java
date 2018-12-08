package org.tomek.podcaster.tokfm.model;

import java.io.Serializable;

public class Category implements Serializable {
    private final int id;
    private final String name;
    private final String[] authors;
    private final String url;
    private final String picHref;

    public Category(int id, String name, String[] authors, String url, String picHref) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.url = url;
        this.picHref = picHref;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getUrl() {
        return url;
    }

    public String getPicHref() {
        return picHref;
    }
}
