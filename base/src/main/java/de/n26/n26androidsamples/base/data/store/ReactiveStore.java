package de.n26.n26androidsamples.base.data.store;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import polanski.option.Option;

/**
 * Interface for any kind of reactive store.
 */
public interface ReactiveStore<Key, Value> {

    void storeSingular(@NonNull final Value model);

    void storeAll(@NonNull final List<Value> modelList);

    void replaceAll(@NonNull final List<Value> modelList);

    Flowable<Option<Value>> getSingularBehaviorStream(@NonNull final Key key);

    Flowable<Option<List<Value>>> getAllBehaviorStream();
}
