package dev.tomek.podcaster.tokfm.podcast;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    static final ZoneId DATE_TIME_ZONE = ZoneId.of("Europe/Warsaw");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(DATE_TIME_ZONE);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withZone(DATE_TIME_ZONE);
    private static final DateTimeFormatter DURATION_FORMATTER = DateTimeFormatter.ofPattern("HH:m[m]:ss");

    public static long parseUnixTimestamp(String dateTimeRaw) {
        try {
            String[] dt = dateTimeRaw.split(" ");
            String dateRaw = null;
            String timeRaw;
            if (dt.length > 0) {
                timeRaw = dt[0];
                if (dt.length > 1) {
                    dateRaw = dt[0];
                    timeRaw = dt[1];
                }
                LocalDate localDate = dateRaw == null ? LocalDate.now() : LocalDate.parse(dateRaw, DATE_FORMATTER);
                return ZonedDateTime.of(localDate, LocalTime.parse(timeRaw, TIME_FORMATTER), DATE_TIME_ZONE).toEpochSecond();
            }
        } catch (RuntimeException e) {
            // ignore
        }
        return 0;
    }

    public static int parseDurationSeconds(String durationRaw) {
        return LocalTime.parse("00:" + durationRaw, DURATION_FORMATTER).toSecondOfDay();
    }
}
