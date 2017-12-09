package de.n26.n26androidsamples.base.data.store;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import polanski.option.Option;

/**
 Interface for any kind of reactive store.
 */
public interface ReactiveStore<Key, Value> {

    void storeSingular(@NonNull final Value model);

    void storeAll(@NonNull final List<Value> modelList);

    void replaceAll(@NonNull final List<Value> modelList);

    Observable<Option<Value>> getSingular(@NonNull final Key key);

    Observable<Option<List<Value>>> getAll();
}
