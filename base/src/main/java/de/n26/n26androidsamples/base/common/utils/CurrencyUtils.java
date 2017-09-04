package de.n26.n26androidsamples.base.common.utils;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

public class CurrencyUtils {

    @Inject
    CurrencyUtils() { }

    public String formatAmount(final double amount) {
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(amount);
    }
}
