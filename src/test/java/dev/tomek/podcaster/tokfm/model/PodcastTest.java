package dev.tomek.podcaster.tokfm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PodcastTest {
    @Test
    void canBuildPodcast() {
        final int id = 44233;
        final String title = "Podcast for testing";
        final int duration = 15001900;
        final String[] guests = {"Twoja Matka"};
        final long airTime = 1421734530;
        Podcast podcast = new Podcast(id, title, duration, guests, airTime);
        assertEquals(id, podcast.getId());
        assertEquals(title, podcast.getTitle());
        assertEquals(duration, podcast.getDuration());
        assertEquals(guests, podcast.getGuests());
        assertEquals(airTime, podcast.getAirTime());
    }
}