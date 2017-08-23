package de.n26.n26androidsamples.base.data.cache;

import com.google.auto.value.AutoValue;

import android.support.annotation.NonNull;

/**
 * Cache entry that contains the object and the creation timestamp.
 */
@AutoValue
abstract class CacheEntry<T> {

    @NonNull
    abstract T cachedObject();

    abstract long creationTimestamp();

    static <T> Builder<T> builder() {
        return new AutoValue_CacheEntry.Builder<>();
    }

    @AutoValue.Builder
    interface Builder<T> {

        Builder<T> cachedObject(T object);

        Builder<T> creationTimestamp(long creationTimestamp);

        CacheEntry<T> build();
    }
}
