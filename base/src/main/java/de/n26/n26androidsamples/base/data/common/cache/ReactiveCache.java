package de.n26.n26androidsamples.base.data.common.cache;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.n26.n26androidsamples.base.data.common.store.ReactivePersistence;
import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import polanski.option.Option;
import polanski.option.function.Func1;

import static polanski.option.Option.ofObj;

/**
 * TODO: Create documentation
 * @param <Key>
 * @param <Value>
 */
public class ReactiveCache<Key, Value> implements ReactivePersistence.Memory<Key, Value> {

    private final Cache<Key, Value> cache;

    private final Func1<Value, Key> extractKeyFromModel;

    private final FlowableProcessor<Option<List<Value>>> allProcessor;

    private final Map<Key, FlowableProcessor<Option<Value>>> processorMap = new HashMap<>();

    public ReactiveCache(@NonNull final Func1<Value, Key> extractKeyFromModel, @NonNull final Cache<Key, Value> cache) {
        this.allProcessor = PublishProcessor.<Option<List<Value>>>create().toSerialized();
        this.extractKeyFromModel = extractKeyFromModel;
        this.cache = cache;
    }

    public void store(@NonNull final Value model) {
        final Key key = extractKeyFromModel.call(model);
        storeInInternalCache(model);
        getOrCreateSubjectForKey(key).onNext(ofObj(model));
        // One item has been added/updated, notify to all as well
        allProcessor.onNext(cache.getAll());
    }

    public void storeAll(@NonNull final List<Value> modelList) {
        // Storing one by one in the cache has a performance cost since the cache is synchronized. A possible improvement is to adapt the cache so
        // it can take also a list of models.
        for (Value model : modelList) {
            storeInInternalCache(model);
        }
        allProcessor.onNext(ofObj(modelList));
        // Publish in all the existing single item streams.
        // This could be improved publishing only in the items that changed. Maybe use DiffUtils?
        publishInEachKey();
    }

    public void replaceAll(@NonNull final List<Value> modelList) {
        cache.clear();
        storeAll(modelList);
    }

    private void storeInInternalCache(@NonNull final Value model) {
        final Key key = extractKeyFromModel.call(model);
        cache.store(key, model);
    }

    @NonNull
    public Flowable<Option<Value>> getBehaviorStream(@NonNull final Key key) {
        final Option<Value> model = cache.get(key);
        return getOrCreateSubjectForKey(key).startWith(model);
    }

    @NonNull
    public Flowable<Option<List<Value>>> getAllBehaviorStream() {
        final Option<List<Value>> modelList = cache.getAll();
        return allProcessor.startWith(modelList);
    }

    /**
     Publishes the cached data in each independent stream only if it exists already.
     */
    private void publishInEachKey() {
        final Set<Key> keySet;
        synchronized (processorMap) {
            keySet = new HashSet<>(processorMap.keySet());
        }
        for (Key key : keySet) {
            publishInKey(key, cache.get(key));
        }
    }

    /**
     Publishes the cached value if there is an already existing stream for the passed key. The case where there isn't a stream for the passed key means
     that the data for this key is not being consumed and therefore there is no need to publish.
     */
    private void publishInKey(@NonNull final Key key, @NonNull final Option<Value> model) {
        final FlowableProcessor<Option<Value>> processor;
        synchronized (processorMap) {
            processor = processorMap.get(key);
        }
        ofObj(processor).ifSome(it -> it.onNext(model));
    }

    @NonNull
    private FlowableProcessor<Option<Value>> getOrCreateSubjectForKey(@NonNull final Key key) {
        synchronized (processorMap) {
            return ofObj(processorMap.get(key)).orDefault(() -> createAndStoreNewSubjectForKey(key));
        }
    }

    @NonNull
    private FlowableProcessor<Option<Value>> createAndStoreNewSubjectForKey(@NonNull final Key key) {
        final FlowableProcessor<Option<Value>> processor = PublishProcessor.<Option<Value>>create().toSerialized();
        synchronized (processorMap) {
            processorMap.put(key, processor);
        }
        return processor;
    }
}
