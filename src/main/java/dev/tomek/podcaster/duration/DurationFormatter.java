package dev.tomek.podcaster.duration;

import java.time.Duration;
import java.time.LocalTime;

public class DurationFormatter {
    public String format(Duration duration) {
        return LocalTime.ofSecondOfDay(duration.getSeconds()).toString().substring(duration.toHoursPart() < 1 ? 3 : 0);
    }
}
