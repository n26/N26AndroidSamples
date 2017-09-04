package de.n26.n26androidsamples.base.common.rx;

import org.junit.Before;
import org.junit.Test;

import de.n26.n26androidsamples.base.BaseTest;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import polanski.option.Option;

public class UnwrapOptionTransformerTest extends BaseTest {

    private UnwrapOptionTransformer<Object> transformer;

    @Before
    public void setUp() {
        transformer = new UnwrapOptionTransformer<>();
    }

    @Test
    public void transformerFiltersOutNone() {
        Flowable<Option<Object>> source = Flowable.just(Option.none());

        TestSubscriber<Object> ts = new TestSubscriber<>();
        transformer.apply(source).subscribe(ts);

        ts.assertNoValues();
    }

    @Test
    public void transformerUnwrapsSome() {
        Object object = new Object();
        Flowable<Option<Object>> source = Flowable.just(Option.ofObj(object));

        TestSubscriber<Object> ts = new TestSubscriber<>();
        transformer.apply(source).subscribe(ts);

        ts.assertValue(object);
    }
}