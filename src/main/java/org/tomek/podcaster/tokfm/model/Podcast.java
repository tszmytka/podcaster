package org.tomek.podcaster.tokfm.model;

import java.io.Serializable;

public class Podcast implements Serializable {
    private final int id;
    private final String title;
    private final int duration;
    private final String[] guests;

    public Podcast(int id, String title, int duration, String[] guests) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.guests = guests;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String[] getGuests() {
        return guests;
    }
}
