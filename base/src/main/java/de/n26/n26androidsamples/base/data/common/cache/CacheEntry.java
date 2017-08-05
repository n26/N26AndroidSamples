package de.n26.n26androidsamples.base.data.common.cache;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 Cache entry that contains the object and the creation timestamp.
 */
@AutoValue
abstract class CacheEntry<T> {

    @NonNull
    abstract T cachedObject();

    abstract long creationTimestamp();

    static <T> CacheEntry<T> create(@NonNull final T cachedObject, final long creationTimestampMillis) {
        return new AutoValue_CacheEntry<>(cachedObject, creationTimestampMillis);
    }
}
