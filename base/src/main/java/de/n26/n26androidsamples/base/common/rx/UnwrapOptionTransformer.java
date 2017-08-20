package de.n26.n26androidsamples.base.common.rx;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import polanski.option.Option;
import polanski.option.OptionUnsafe;

/**
 Filters out all Option of NONE if any, but if Some, then unwraps and returns the value.
 */
public final class UnwrapOptionTransformer<T> implements FlowableTransformer<Option<T>, T> {

    @Override
    public Publisher<T> apply(final Flowable<Option<T>> upstream) {
        return upstream.filter(Option::isSome)
                       .map(OptionUnsafe::getUnsafe);
    }
}
