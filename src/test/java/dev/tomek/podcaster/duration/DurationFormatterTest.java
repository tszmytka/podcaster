package dev.tomek.podcaster.duration;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationFormatterTest {
    @Test
    void canFormatCorrectlyWithSimpleFormat() {
        DurationFormatter df = new DurationFormatter();
        Map<Duration, String> inputData = Map.of(
            Duration.ofSeconds(0), "00:00",
            Duration.ofSeconds(13), "00:00:13",
            Duration.ofSeconds(3 * 60 + 27), "00:03:27",
            Duration.ofSeconds(2 * 60 * 60 + 48 * 60 + 51), "02:48:51",
            Duration.ofSeconds(11 * 60 * 60 + 38 * 60 + 1), "11:38:01",
            Duration.ofHours(7).plusMinutes(59).plusSeconds(59), "07:59:59"
        );

        for (Map.Entry<Duration, String> entry : inputData.entrySet()) {
            assertEquals(entry.getValue(), df.format(entry.getKey()));
        }
    }
}