package de.n26.n26androidsamples.base.common.utils;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

public final class TimeUtils {

    @Inject
    public TimeUtils() { }

    /**
     Formats the passed ISO 8601 formatted date string to a date string following the passed format pattern.

     @param isoTimeString the string of the date formatted in ISO 8601
     @param formatPattern the pattern to format the ISO date string
     */
    @NonNull
    public String formatIsoStringToDate(@NonNull final String isoTimeString, @NonNull final String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern, Locale.GERMAN);

        try {
            return sdf.parse(isoTimeString).toString();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error formatting " + isoTimeString + " using the formatPattern " + formatPattern, e);
        }
    }
}
