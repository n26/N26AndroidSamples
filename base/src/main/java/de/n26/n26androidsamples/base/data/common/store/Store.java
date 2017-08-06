package de.n26.n26androidsamples.base.data.common.store;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by Lucia on 06/08/2017.
 */
public interface Store<Key, Value> {

    void store(@NonNull final Value value);

    void storeAll(@NonNull final List<Value> valueList);

    void clear();

    Maybe<Value> get(@NonNull final Key key);

    Maybe<List<Value>> getAll();

    interface MemoryStore<Key, Value> extends Store<Key, Value> {}

    interface DiskStore<Key, Value> extends Store<Key, Value> {}
}
