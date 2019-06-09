package dev.tomek.podcaster.tokfm.model;

import java.io.Serializable;

public class Podcast implements Serializable {
    private final int id;
    private final String title;
    private final int duration;
    private final String[] guests;
    private final long airTime;

    public Podcast(int id, String title, int duration, String[] guests, long airTime) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.guests = guests;
        this.airTime = airTime;
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

    public long getAirTime() {
        return airTime;
    }
}
