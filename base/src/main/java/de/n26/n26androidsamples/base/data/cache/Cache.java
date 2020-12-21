package de.n26.n26androidsamples.base.data.cache;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import autovalue.shaded.com.google$.common.base.$Ascii;
import de.n26.n26androidsamples.base.common.providers.TimestampProvider;
import de.n26.n26androidsamples.base.common.rx.SchedulerProvider;
import de.n26.n26androidsamples.base.common.utils.ListUtils;
import de.n26.n26androidsamples.base.data.store.Store.MemoryStore;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import polanski.option.Option;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

/**
 Generic memory cache with timeout for the entries.
 */
public class Cache<Key, Value> implements MemoryStore<Key, Value> {

    @NonNull
    private final TimestampProvider timestampProvider;

    @NonNull
    private final Function<Value, Key> extractKeyFromModel;

    @NonNull
    private final SchedulerProvider schedulerProvider;

    @NonNull
    private final Option<Long> itemLifespanMs;

    private final Map<Key, CacheEntry<Value>> cache = new ConcurrentHashMap<>();

    public Cache(@NonNull final Function<Value, Key> extractKeyFromModel,
                 @NonNull final TimestampProvider timestampProvider,
                 @NotNull final SchedulerProvider provider) {
        this(extractKeyFromModel, timestampProvider, provider, none());
    }

    public Cache(@NonNull final Function<Value, Key> extractKeyFromModel,
                 @NonNull final TimestampProvider timestampProvider,
                 @NotNull final SchedulerProvider provider,
                 final long timeoutMs) {
        this(extractKeyFromModel, timestampProvider, provider, ofObj(timeoutMs));
    }

    private Cache(@NonNull final Function<Value, Key> extractKeyFromModel,
                  @NonNull final TimestampProvider timestampProvider,
                  @NotNull final SchedulerProvider provider,
                  @NonNull final Option<Long> timeoutMs) {
        this.timestampProvider = timestampProvider;
        this.itemLifespanMs = timeoutMs;
        this.extractKeyFromModel = extractKeyFromModel;
        this.schedulerProvider = provider;
    }

    @Override
    public void putSingular(@NonNull Value value) {
        Single.fromCallable(() -> extractKeyFromModel.apply(value))
              .subscribeOn(schedulerProvider.computation())
              .subscribe(key -> cache.put(key, createCacheEntry(value)));
    }

    @Override
    public void putAll(@NonNull List<Value> values) {
        Observable.fromIterable(values)
                  .toMap(extractKeyFromModel, this::createCacheEntry)
                  .subscribeOn(schedulerProvider.computation())
                  .subscribe(cache::putAll);
    }

    @Override
    @NonNull
    public Maybe<Value> getSingular(@NonNull final Key key) {
        return Maybe.fromCallable(() -> cache.containsKey(key))
                    .filter(isPresent -> isPresent)
                    .map(__ -> cache.get(key))
                    .filter(this::notExpired)
                    .map(CacheEntry::cachedObject)
                    .subscribeOn(schedulerProvider.computation());
    }

    @Override
    @NonNull
    public Maybe<List<Value>> getAll() {
        return Observable.fromIterable(cache.values())
                         .filter(this::notExpired)
                         .map(CacheEntry::cachedObject)
                         .toList()
                         .filter(ListUtils::isNotEmpty)
                         .subscribeOn(schedulerProvider.computation());
    }

    @Override
    public void clear() {
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
