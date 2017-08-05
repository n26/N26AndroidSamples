package de.n26.n26androidsamples.base.data.common.cache;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.n26.n26androidsamples.base.common.providers.TimestampProvider;
import de.n26.n26androidsamples.base.common.utils.ListUtils;
import polanski.option.Option;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

/**
 Generic cache with timeout for the entries.
 */
public class Cache<Key, Value> {

    @NonNull
    private final TimestampProvider timestampProvider;

    @NonNull
    private final Option<Long> itemLifespanMs;

    private final Map<Key, CacheEntry<Value>> cache = new HashMap<>();

    public Cache(@NonNull final TimestampProvider timestampProvider) {
        this.timestampProvider = timestampProvider;
        itemLifespanMs = none();
    }

    public Cache(@NonNull final TimestampProvider timestampProvider, final long timeoutMs) {
        this.timestampProvider = timestampProvider;
        itemLifespanMs = ofObj(timeoutMs);
    }

    /**
     Stores the passed model in the cache.
     @param key the unique key for the object in the cache
     */
    void store(@NonNull final Key key, @NonNull final Value model) {
        final CacheEntry<Value> cacheEntry = CacheEntry.create(model, timestampProvider.currentTimeMillis());
        synchronized (cache) {
            cache.put(key, cacheEntry);
        }
    }

    /**
     Returns SOME of the cached object for the given key if it existed, NONE otherwise. The cached object should not be modified, modifying this object
     can result in inconsistencies in the cache. A copy of the object should be made in the case that it needs some modification.
     */
    @NonNull
    Option<Value> get(@NonNull final Key key) {
        synchronized (cache) {
            // Might be null if no entry with the specified key was previously stored
            final CacheEntry<Value> cacheEntry = cache.get(key);
            return ofObj(cacheEntry).filter(this::notExpired)
                                    .map(CacheEntry::cachedObject);
        }
    }

    /**
     Returns SOME of a list containing all cached objects, NONE if the cache is empty. The cached objects should not be modified, modifying this object
     can result in inconsistencies in the cache. A copy of the object should be made in the case that it needs some modification.
     */
    Option<List<Value>> getAll() {
        final List<Value> modelList = new ArrayList<>();
        synchronized (cache) {
            for (Key key : cache.keySet()) {
                get(key).ifSome(modelList::add);
            }
        }
        return ofObj(modelList).filter(list -> ListUtils.isNotEmpty(list));
    }

    /**
     Clears the cache removing all the cached objects.
     */
    void clear() {
        synchronized (cache) {
            cache.clear();
        }
    }

    private boolean notExpired(@NonNull final CacheEntry<Value> cacheEntry) {
        return itemLifespanMs.match(lifespanMs -> cacheEntry.creationTimestamp() + lifespanMs > timestampProvider.currentTimeMillis(),
            // When lifespan was not set the items in the cache never expire
            () -> true);
    }
}
