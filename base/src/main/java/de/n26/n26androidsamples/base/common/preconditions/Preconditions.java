package de.n26.n26androidsamples.base.common.preconditions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Preconditions {

    /**
     * Checks if the reference is not null.
     *
     * @param reference an object reference
     * @return the non-null reference
     * @throws NullPointerException if {@code reference} is null
     */
    @NonNull
    public static <T> T get(@Nullable final T reference) {
        if (reference == null) {
            throw new NullPointerException("Assertion for a nonnull object failed.");
        }
        return reference;
    }

    /**
     * Checks if the reference is not null.
     *
     * @param reference object reference
     * @return non-null reference
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Checks if the reference is not null.
     *
     * @param reference    object reference
     * @param errorMessage message used if the check fails
     * @return non-null reference
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference, @NonNull final String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(get(errorMessage));
        }
        return reference;
    }

    private Preconditions() {
        throw new AssertionError("Don't create instances of this object");
    }
}
