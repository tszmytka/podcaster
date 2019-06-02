package dev.tomek.podcaster.duration;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationFormatterTest {
    @Test
    void canFormatCorrectly() {
        DurationFormatter df = new DurationFormatter("%d", "%d", "%d", ":");
        assertEquals("0:45", df.format(Duration.ofSeconds(45)));
    }
}