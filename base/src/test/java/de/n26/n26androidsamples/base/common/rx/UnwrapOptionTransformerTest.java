package de.n26.n26androidsamples.base.common.rx;

import org.junit.Before;
import org.junit.Test;

import de.n26.n26androidsamples.base.test_common.BaseTest;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import polanski.option.Option;

public class UnwrapOptionTransformerTest extends BaseTest {

    private UnwrapOptionTransformer<Object> transformer;

    @Before
    public void setUp() {
        transformer = new UnwrapOptionTransformer<>();
    }

    @Test
    public void transformerFiltersOutNone() {
        Observable<Option<Object>> source = Observable.just(Option.none());

        TestObserver<Object> ts = new TestObserver<>();
        transformer.apply(source).subscribe(ts);

        ts.assertNoValues();
    }

    @Test
    public void transformerUnwrapsSome() {
        Object object = new Object();
        Observable<Option<Object>> source = Observable.just(Option.ofObj(object));

        TestObserver<Object> ts = new TestObserver<>();
        transformer.apply(source).subscribe(ts);
        ts.assertValue(object);
    }
}