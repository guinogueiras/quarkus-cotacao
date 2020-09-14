package br.com.cotacao.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateUtils {

    private static final String DATE_FORMAT = "MM-dd-uuuu";
    private static final String DATE_TIME_API_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static LocalDate getLocalDate(final String dateStr) {

        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(dateStr, formatter);
        } catch (final Exception e) {
            return null;
        }
    }
    
    public static LocalDateTime getLocalDateTime(String dateTimeStr) {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_API_FORMAT);

            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (final Exception e) {
            return null;
        }
    }

    public static String getStringDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return localDate.format(formatter);
    }
}
