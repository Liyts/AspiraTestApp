package org.example;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String getFormattedDate(long date) {
        Instant instant = Instant.ofEpochMilli(date);
        ZoneId eestZone = ZoneId.of("Europe/Kiev");
        ZonedDateTime eestDateTime = ZonedDateTime.ofInstant(instant, eestZone);
        ZonedDateTime utcDateTime = eestDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return utcDateTime.format(formatter);
    }
}
