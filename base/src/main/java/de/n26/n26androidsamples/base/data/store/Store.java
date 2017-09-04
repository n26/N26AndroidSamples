package de.n26.n26androidsamples.base.data.store;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Maybe;


/**
 * Interface for any type of store. Don't implement this directly,
 * use {@link MemoryStore} or {@link DiskStore} so it is more
 * descriptive.
 */
public interface Store<Key, Value> {

    void putSingular(@NonNull final Value value);

    void putAll(@NonNull final List<Value> valueList);

    void clear();

    @NonNull
    Maybe<Value> getSingular(@NonNull final Key key);

    @NonNull
    Maybe<List<Value>> getAll();

    /**
     * More descriptive interface for memory based stores.
     */
    interface MemoryStore<Key, Value> extends Store<Key, Value> {
    }

    /**
     * More descriptive interface for disk based stores.
     */
    interface DiskStore<Key, Value> extends Store<Key, Value> {
    }
}
