package de.n26.n26androidsamples.base.data.cache;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.n26.n26androidsamples.base.common.preconditions.AndroidPreconditions;
import de.n26.n26androidsamples.base.common.providers.TimestampProvider;
import de.n26.n26androidsamples.base.common.utils.ListUtils;
import de.n26.n26androidsamples.base.data.store.Store.MemoryStore;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import polanski.option.Option;
import polanski.option.function.Func1;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

/**
 * Generic memory cache with timeout for the entries.
 */
public class Cache<Key, Value> implements MemoryStore<Key, Value> {

    @NonNull
    private final TimestampProvider timestampProvider;

    @NonNull
    private final AndroidPreconditions androidPreconditions;

    @NonNull
    private final Func1<Value, Key> extractKeyFromModel;

    @NonNull
    private final Option<Long> itemLifespanMs;

    private final Map<Key, CacheEntry<Value>> cache = new ConcurrentHashMap<>();

    public Cache(@NonNull final TimestampProvider timestampProvider,
                 @NonNull final AndroidPreconditions androidPreconditions,
                 @NonNull final Func1<Value, Key> extractKeyFromModel) {
        this(timestampProvider, androidPreconditions, extractKeyFromModel, none());
    }

    public Cache(@NonNull final TimestampProvider timestampProvider,
                 @NonNull final AndroidPreconditions androidPreconditions,
                 @NonNull final Func1<Value, Key> extractKeyFromModel,
                 final long timeoutMs) {
        this(timestampProvider, androidPreconditions, extractKeyFromModel, ofObj(timeoutMs));
    }

    private Cache(@NonNull final TimestampProvider timestampProvider,
                  @NonNull final AndroidPreconditions androidPreconditions,
                  @NonNull final Func1<Value, Key> extractKeyFromModel,
                  @NonNull final Option<Long> timeoutMs) {
        this.timestampProvider = timestampProvider;
        this.androidPreconditions = androidPreconditions;
        this.itemLifespanMs = timeoutMs;
        this.extractKeyFromModel = extractKeyFromModel;
    }

    @Override
    public void put(@NonNull Value value) {
        androidPreconditions.assertWorkerThread();

        final Key key = extractKeyFromModel.call(value);
        cache.put(key, createCacheEntry(value));
    }

    @Override
    public void putAll(@NonNull List<Value> values) {
        androidPreconditions.assertWorkerThread();

        Observable.fromIterable(values)
                  .toMap(extractKeyFromModel::call, this::createCacheEntry)
                  .subscribe(cache::putAll);
    }

    @Override
    @NonNull
    public Maybe<Value> get(@NonNull final Key key) {
        androidPreconditions.assertWorkerThread();

        return Maybe.just(cache.get(key))
                    .filter(entry -> entry != null)
                    .filter(this::notExpired)
                    .map(CacheEntry::cachedObject);
    }

    @Override
    @NonNull
    public Maybe<List<Value>> getAll() {
        androidPreconditions.assertWorkerThread();

        return Observable.fromIterable(cache.values())
                         .map(CacheEntry::cachedObject)
                         .toList()
                         .filter(ListUtils::isNotEmpty);
    }

    @Override
    public void clear() {
        androidPreconditions.assertWorkerThread();

        cache.clear();
    }

    @NonNull
    private CacheEntry<Value> createCacheEntry(@NonNull final Value value) {
        return CacheEntry.<Value>builder().cachedObject(value)
                                          .creationTimestamp(timestampProvider.currentTimeMillis())
                                          .build();
    }

    private boolean notExpired(@NonNull final CacheEntry<Value> cacheEntry) {
        return itemLifespanMs.match(lifespanMs -> cacheEntry.creationTimestamp() + lifespanMs > timestampProvider.currentTimeMillis(),
            // When lifespan was not set the items in the cache never expire
            () -> true);
    }
}
