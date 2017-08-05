package de.n26.n26androidsamples.base.data.common.store;

/**
 * Created by Lucia on 23/07/2017.
 */

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import polanski.option.Option;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

/**
 The store takes care of handling and abstracting all the different storage systems available: cache, db, shared prefs, files, etc. Right now it
 only wraps the cache because there is no other storage system integrated. This means that the implementation of this store is not final and will be
 extended in the future.
 */
public class ReactiveStore<Key, Value> {

    @NonNull
    private final ReactivePersistence.Memory<Key, Value> cache;

    @NonNull
    private final Option<ReactivePersistence.Disk<Key, Value>> disk;

    public ReactiveStore(@NonNull final ReactivePersistence.Memory<Key, Value> cache) {
        this.cache = cache;
        disk = none();
    }

    public ReactiveStore(@NonNull final ReactivePersistence.Memory<Key, Value> cache, @NonNull final ReactivePersistence.Disk<Key, Value> disk) {
        this.cache = cache;
        this.disk = ofObj(disk);
    }

    public void store(@NonNull final Value model) {
        cache.store(model);
    }

    public void storeAll(@NonNull final List<Value> modelList) {
        cache.storeAll(modelList);
    }

    public void replaceAll(@NonNull final List<Value> modelList) {
        cache.replaceAll(modelList);
    }

    @NonNull
    public Flowable<Option<Value>> getBehaviorStream(@NonNull final Key key) {
        return cache.getBehaviorStream(key);
    }

    @NonNull
    public Flowable<Option<List<Value>>> getAllBehaviorStream() {
        return cache.getAllBehaviorStream();
    }
}
