package com.planchella.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy h:mm a");

    public static String formatIsoDate(String isoDateString) {
        if (isoDateString == null || isoDateString.isEmpty()) {
            return "";
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(isoDateString, INPUT_FORMATTER);
            return dateTime.format(DISPLAY_FORMATTER);
        } catch (Exception e) {
            return isoDateString; // Return original if parsing fails
        }
    }

    public static String cleanToIcsDate(String isoDate) {
        if (isoDate == null) return "";
        return isoDate.replaceAll("[-:]", "").replaceAll("\\.\\d{3}", "");
    }
}
