package dev.tomek.podcaster.duration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DurationFormatter {
    private final String hourFormat;
    private final String minuteFormat;
    private final String secondFormat;
    private final String delimiter;

    public DurationFormatter(String hourFormat, String minuteFormat, String secondFormat, String delimiter) {
        this.hourFormat = hourFormat;
        this.minuteFormat = minuteFormat;
        this.secondFormat = secondFormat;
        this.delimiter = delimiter;
    }

    public String format(Duration duration) {
        StringBuilder sb = new StringBuilder();
        long hoursPart = duration.toHours();
        long minutesPart = duration.toMinutes();
        long secondsPart = duration.getSeconds();
        List<Number> durationVals = new ArrayList<>();
        if (hoursPart > 0) {
            sb.append(hourFormat).append(delimiter);
            durationVals.add(hoursPart);
        }
        sb.append(minuteFormat).append(delimiter).append(secondFormat);
        durationVals.add(minutesPart);
        durationVals.add(secondsPart);
        return String.format(sb.toString(), durationVals.toArray());
    }
}
