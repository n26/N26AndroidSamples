package de.n26.n26androidsamples.base.common.utils;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import javax.inject.Inject;

public final class StringUtils {

    @Inject
    StringUtils() {}

    /**
     Use to replace the placeholders for strings that use the format "text {{placeholder}} text".

     @param string        string to apply substitutions on
     @param substitutions key value pairs (placeholder, substitutionString)
     @return string with substitutions applied
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public String applySubstitutionsToString(@NonNull final String string, @NonNull final Pair<String, String>... substitutions) {
        String primaryString = string;

        for (final Pair<String, String> substitution : substitutions) {
            primaryString = primaryString.replace("{{" + substitution.first + "}}", substitution.second);
        }
        return primaryString;
    }
}
