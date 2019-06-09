package dev.tomek.podcaster.tokfm.podcast;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeParserTest {
    @Test
    void canInterpretCorrectFormat() {
        Map<String, Long> inputData = Map.of(
            "02.06.2019 09:00", 1559458800L,
            "09.06.2019 16:08", 1560089280L,
            "06.07.2019 13:20", 1562412000L,
            "16:45", ZonedDateTime.now().withHour(16).withMinute(45).withSecond(0).withZoneSameInstant(DateTimeParser.DATE_TIME_ZONE).toEpochSecond()
        );

        for (Map.Entry<String, Long> entry : inputData.entrySet()) {
            assertEquals(entry.getValue().longValue(), DateTimeParser.parseUnixTimestamp(entry.getKey()));
        }
    }

    @Test
    void willNotBreakOnInvalidDateTime() {
        Map<String, Long> inputData = Map.of(
            "", 0L,
            "blah", 0L,
            "35.97.-1 47:99", 0L
        );

        for (Map.Entry<String, Long> entry : inputData.entrySet()) {
            assertEquals(entry.getValue().longValue(), DateTimeParser.parseUnixTimestamp(entry.getKey()));
        }
    }

    @Test
    void canParseDurationSeconds() {
        Map<String, Long> inputData = Map.of(
            "00:00", 0L,
            "00:01", 1L,
            "15:21", 921L,
            "59:59", 3_599L,
            "5:31", 331L
        );

        for (Map.Entry<String, Long> entry : inputData.entrySet()) {
            assertEquals(entry.getValue().longValue(), DateTimeParser.parseDurationSeconds(entry.getKey()));
        }
    }
}