package de.n26.n26androidsamples.base.common.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public final class TimeUtils {

    @Inject
    TimeUtils() { }

    /**
     Formats the passed date string following the passed format pattern.

     @param dateString    the string of the date to format
     @param formatPattern the pattern to format date string
     */
    @NonNull
    public String formatDateString(@NonNull final String dateString, @NonNull final String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern, Locale.GERMAN);
        Date date = parseStringToDateWithPassedPattern(dateString, "yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     Parses the passed string to a date with the passed format.

     @param dateString    the string of the date to parse
     @param formatPattern the pattern to format date string
     @throws RuntimeException if there is any problems while parsing
     */
    @NonNull
    public Date parseStringToDateWithPassedPattern(@NonNull final String dateString, @NonNull final String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern, Locale.GERMAN);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error formatting " + dateString + " with pattern " + formatPattern + " to Date");
        }
    }
}
