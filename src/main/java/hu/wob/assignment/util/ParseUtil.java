package hu.wob.assignment.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ParseUtil {

    public static String getStringValue(Object value) {
        return value != null ? value.toString() : null;
    }

    public static Double getDoubleValue(Object value) {
        return value instanceof Number ? ((Number) value).doubleValue() : null;
    }

    public static Integer getIntegerValue(Object value) {
        return value instanceof Number ? ((Number) value).intValue() : null;
    }

    public static LocalDate getLocalDateValue(Object value, String formatPattern) {
        if (value instanceof String) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
            return LocalDate.parse((String) value, formatter);
        }
        return null;
    }
}
