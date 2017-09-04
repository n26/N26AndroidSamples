package de.n26.n26androidsamples.base.data.store;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.n26.n26androidsamples.base.BaseTest;
import de.n26.n26androidsamples.base.data.cache.Cache;
import io.reactivex.Maybe;

import static polanski.option.Option.none;
import static polanski.option.Option.ofObj;

public class MemoryReactiveStoreTest extends BaseTest {

    @Mock
    private Cache<String, TestObject> cache;

    private MemoryReactiveStore<String, TestObject> reactiveStore;

    @Before
    public void setUp() {
        reactiveStore = new MemoryReactiveStore<>(TestObject::id, cache);
    }

    @Test
    public void noneIsEmittedWhenCacheIsEmpty() {
        new ArrangeBuilder().withEmptyCache();

        reactiveStore.getSingular("ID1").test().assertValue(none());
        reactiveStore.getAll().test().assertValue(none());
    }

    @Test
    public void lastStoredObjectIsEmittedAfterSubscription() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObject(model)
                            .withCachedObjectList(Collections.singletonList(model));

        reactiveStore.storeSingular(model);
        reactiveStore.getSingular("ID1").test().assertValue(ofObj(model));
    }

    @Test
    public void streamsEmitWhenSingleObjectIsStored() {
        List<TestObject> list = createTestObjectList();
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(list)
                            .withCachedObject(model);

        reactiveStore.storeSingular(model);

        reactiveStore.getSingular("ID1").test().assertValue(ofObj(model));
        reactiveStore.getAll().test().assertValue(ofObj(list));
    }

    @Test
    public void streamsEmitWhenObjectListIsStored() {
        List<TestObject> list = createTestObjectList();
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(list)
                            .withCachedObject(model);

        reactiveStore.storeAll(list);

        reactiveStore.getSingular("ID1").test().assertValue(ofObj(model));
        reactiveStore.getAll().test().assertValue(ofObj(list));
    }

    @Test
    public void streamsEmitWhenObjectListIsReplaced() {
        List<TestObject> list = createTestObjectList();
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(list)
                            .withCachedObject(model);

        reactiveStore.replaceAll(list);

        reactiveStore.getSingular("ID1").test().assertValue(ofObj(model));
        reactiveStore.getAll().test().assertValue(ofObj(list));
    }

    @Test
    public void objectIsStoredInCache() {
        TestObject model = new TestObject("ID1");
        new ArrangeBuilder().withCachedObjectList(Collections.singletonList(model));

        reactiveStore.storeSingular(model);

        Mockito.verify(cache).putSingular(model);
    }

    @Test
    public void objectListIsStoredInCache() {
        List<TestObject> list = createTestObjectList();

        reactiveStore.storeAll(list);

        Mockito.verify(cache).putAll(list);
    }

    @Test
    public void cacheIsClearedInReplaceAll() {
        List<TestObject> list = createTestObjectList();

        reactiveStore.replaceAll(list);

        Mockito.verify(cache).clear();
    }

    private static List<TestObject> createTestObjectList() {
        return new ArrayList<TestObject>() {{
            add(new TestObject("ID1"));
            add(new TestObject("ID2"));
            add(new TestObject("ID3"));
        }};
    }

    private static class TestObject {

        private final String id;

        private TestObject(final String id) {this.id = id;}

        private String id() {
            return id;
        }

        @Override
        public String toString() {
            return id;
        }
    }

    private class ArrangeBuilder {

        private ArrangeBuilder withCachedObject(TestObject object) {
            Mockito.when(cache.getSingular(object.id())).thenReturn(Maybe.just(object));
            return this;
        }

        private ArrangeBuilder withCachedObjectList(List<TestObject> objectList) {
            Mockito.when(cache.getAll()).thenReturn(Maybe.just(objectList));
            return this;
        }

        private ArrangeBuilder withEmptyCache() {
            Mockito.when(cache.getSingular(Mockito.anyString())).thenReturn(Maybe.empty());
            Mockito.when(cache.getAll()).thenReturn(Maybe.empty());
            return this;
        }
    }
}
