package de.n26.n26androidsamples.base.data.common.network;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.schedulers.Schedulers;
import polanski.option.Option;
import polanski.option.Unit;

/**
 Fetches data from remote and stores it. Keeps track of the ongoing fetches and doesn't trigger a new one when there is another fetch still pending.
 Used for core data that doesn't change fast.
 */
public abstract class Fetcher<Params, Value> {

    @NonNull
    private final Map<Option<Params>, FlowableProcessor<Unit>> fetchProcessorMap = new ConcurrentHashMap<>();

    @NonNull
    public Flowable<Unit> fetchSingle(@NonNull final Option<Params> params) {
        if (isOngoingFetch(params)) {
            // There is a fetch pending for the passed params, do not trigger a new one, just return the stream for the one ongoing.
            return fetchProcessorMap.get(params).observeOn(Schedulers.computation());
        }

        final FlowableProcessor<Unit> fetchProcessor = BehaviorProcessor.<Unit>create().toSerialized();
        fetchProcessorMap.put(params, fetchProcessor);

        getFetchSingle(params).subscribeOn(Schedulers.io())
                              .observeOn(Schedulers.computation())
                              .doOnNext(this::store)
                              .doOnComplete(() -> fetchProcessorMap.remove(params))
                              .doOnError(__ -> fetchProcessorMap.remove(params))
                              .map(Unit::asUnit)
                              .subscribe(fetchProcessor);

        return fetchProcessor.observeOn(Schedulers.computation());
    }

    private boolean isOngoingFetch(@NonNull final Option<Params> params) {
        return fetchProcessorMap.get(params) != null;
    }

    protected abstract void store(@NonNull final Value model);

    @NonNull
    protected abstract Flowable<Value> getFetchSingle(@NonNull final Option<Params> params);
}
