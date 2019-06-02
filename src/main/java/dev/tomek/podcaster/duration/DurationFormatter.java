package dev.tomek.podcaster.duration;

import java.time.Duration;
import java.time.LocalTime;

public class DurationFormatter {
    public String format(Duration duration) {
        // todo Allow not rendering hours if it is "00"
        return LocalTime.ofSecondOfDay(duration.getSeconds()).toString();
    }
}
