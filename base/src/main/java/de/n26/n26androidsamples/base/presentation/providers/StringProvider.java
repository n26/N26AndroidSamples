package de.n26.n26androidsamples.base.presentation.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.util.Pair;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.common.utils.StringUtils;
import de.n26.n26androidsamples.base.injection.qualifiers.ForApplication;

/**
 Provides access to string resources to classes that have not access to Context (publishers, mappers, etc.)
 */
public class StringProvider {

    @NonNull
    private final Context context;

    @NonNull
    private final StringUtils stringUtils;

    @Inject
    public StringProvider(@NonNull @ForApplication final Context context, @NonNull final StringUtils stringUtils) {
        this.context = context;
        this.stringUtils = stringUtils;
    }

    @NonNull
    public String getString(@StringRes final int resId) {
        return context.getString(resId);
    }

    @NonNull
    public String getString(@StringRes final int resId, @NonNull final Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    /**
     Use to replace the placeholders for strings that use the format "text {{placeholder}} text".

     @param stringResId   string resource id
     @param substitutions substitutions
     @return string
     */
    @SuppressWarnings("unchecked")
    public String getStringAndApplySubstitutions(@StringRes final int stringResId, @NonNull final Pair<String, String>... substitutions) {
        return stringUtils.applySubstitutionsToString(context.getString(stringResId), substitutions);
    }
}
