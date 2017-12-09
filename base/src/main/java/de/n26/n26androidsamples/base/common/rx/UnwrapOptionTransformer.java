package de.n26.n26androidsamples.base.common.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import polanski.option.Option;
import polanski.option.OptionUnsafe;

/**
 Filters out all Option of NONE if any, but if Some, then unwraps and returns the value.
 */
public final class UnwrapOptionTransformer<T> implements ObservableTransformer<Option<T>, T> {

    @Override
    public ObservableSource<T> apply(final Observable<Option<T>> upstream) {
        return upstream.filter(Option::isSome)
                       .map(OptionUnsafe::getUnsafe);
    }

    public static <T> UnwrapOptionTransformer<T> create() {
        return new UnwrapOptionTransformer<>();
    }
}
