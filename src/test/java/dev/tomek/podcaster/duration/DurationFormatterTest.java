package dev.tomek.podcaster.duration;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationFormatterTest {
    @Test
    void canFormatCorrectly() {
        DurationFormatter df = new DurationFormatter("%d", "%d", "%d", ":");
        Map<String, Duration> inputData = Map.of(
            "0:45", Duration.ofSeconds(45)
        );
        for (Map.Entry<String, Duration> entry : inputData.entrySet()) {
            assertEquals(entry.getKey(), df.format(entry.getValue()));
        }
    }
}