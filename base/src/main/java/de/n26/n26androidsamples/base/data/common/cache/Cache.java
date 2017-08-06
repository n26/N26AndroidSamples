package de.n26.n26androidsamples.base.data.common.cache;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.n26.n26androidsamples.base.common.preconditions.AndroidPreconditions;
import de.n26.n26androidsamples.base.common.providers.TimestampProvider;
import de.n26.n26androidsamples.base.common.utils.ListUtils;
import de.n26.n26androidsamples.base.data.common.store.Store;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import polanski.option.Option;
import polanski.option.function.Func1;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

/**
 * Generic cache with timeout for the entries.
 */
public class Cache<Key, Value> implements Store.MemoryStore<Key, Value> {

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
        this(timestampProvider, androidPreconditions, none(), extractKeyFromModel);
    }

    public Cache(@NonNull final TimestampProvider timestampProvider,
                 @NonNull final AndroidPreconditions androidPreconditions,
                 final long timeoutMs,
                 @NonNull final Func1<Value, Key> extractKeyFromModel) {
        this(timestampProvider, androidPreconditions, ofObj(timeoutMs), extractKeyFromModel);
    }

    private Cache(@NonNull final TimestampProvider timestampProvider,
                  @NonNull final AndroidPreconditions androidPreconditions,
                  @NonNull final Option<Long> timeoutMs,
                  @NonNull final Func1<Value, Key> extractKeyFromModel) {
        this.timestampProvider = timestampProvider;
        this.androidPreconditions = androidPreconditions;
        this.itemLifespanMs = timeoutMs;
        this.extractKeyFromModel = extractKeyFromModel;
    }


    @Override
    public void store(@NonNull Value value) {
        androidPreconditions.assertWorkerThread();

        final Key key = extractKeyFromModel.call(value);
        final CacheEntry<Value> cacheEntry = CacheEntry.create(value, timestampProvider.currentTimeMillis());
        cache.put(key, cacheEntry);

    }

    @Override
    public void storeAll(@NonNull List<Value> values) {
        androidPreconditions.assertWorkerThread();

        Observable.fromIterable(values)
                  .toMap(extractKeyFromModel::call, value -> CacheEntry.create(value, timestampProvider.currentTimeMillis()))
                  .subscribe(cache::putAll);
    }

    @Override
    @NonNull
    public Maybe<Value> get(@NonNull final Key key) {
        androidPreconditions.assertWorkerThread();

        final CacheEntry<Value> cacheEntry = cache.get(key);
        return Maybe.just(cacheEntry)
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

    /**
     * Clears the cache removing all the cached objects.
     */
    @Override
    public void clear() {
        cache.clear();
    }

    private boolean notExpired(@NonNull final CacheEntry<Value> cacheEntry) {
        return itemLifespanMs.match(lifespanMs -> cacheEntry.creationTimestamp() + lifespanMs > timestampProvider.currentTimeMillis(),
            // When lifespan was not set the items in the cache never expire
            () -> true);
    }
}
