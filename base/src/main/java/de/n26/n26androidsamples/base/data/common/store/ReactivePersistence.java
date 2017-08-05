package de.n26.n26androidsamples.base.data.common.store;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import polanski.option.Option;

/**
 Interface for any type of reactive persistence method. Don't implement this directly, use {@link Memory} or {@link Disk} so it is more descriptive.
 */
public interface ReactivePersistence<Key, Value> {

    interface Memory<Key, Value> extends ReactivePersistence<Key, Value> {}

    interface Disk<Key, Value> extends ReactivePersistence<Key, Value> {}

    void store(@NonNull final Value model);

    void storeAll(@NonNull final List<Value> modelList);

    void replaceAll(@NonNull final List<Value> modelList);

    Flowable<Option<Value>> getBehaviorStream(@NonNull final Key key);

    Flowable<Option<List<Value>>> getAllBehaviorStream();
}
