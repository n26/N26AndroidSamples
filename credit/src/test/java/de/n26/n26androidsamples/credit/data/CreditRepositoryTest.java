package de.n26.n26androidsamples.credit.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.n26.n26androidsamples.base.data.store.ReactiveStore;
import de.n26.n26androidsamples.credit.test_common.BaseTest;
import de.n26.n26androidsamples.credit.test_common.TestSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import polanski.option.Option;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreditRepositoryTest extends BaseTest {

    @Mock
    private ReactiveStore<String, CreditDraft> store;

    @Mock
    private CreditService service;

    @Mock
    private CreditDraftMapper mapper;

    private final TestScheduler schedulerProvider = new TestScheduler();

    private CreditRepository repository;

    @Before
    public void setUp() {
        repository = new CreditRepository(store, service, mapper, new TestSchedulerProvider(schedulerProvider));
    }

    @Test
    public void getAllCreditDraftsReturnsStoreObservable() {
        Observable<Option<List<CreditDraft>>> storeObservable = Observable.empty();
        new ArrangeBuilder().withObservableFromStore(storeObservable);

        schedulerProvider.triggerActions();

        assertThat(repository.getAllCreditDrafts()).isEqualTo(storeObservable);
    }

    @Test
    public void fetchCreditDraftsEmitsErrorWhenNetworkServiceErrors() {
        Throwable throwable = Mockito.mock(Throwable.class);
        new ArrangeBuilder().withErrorInCreditDraftsFromService(throwable);

        TestObserver<Void> testObserver = repository.fetchCreditDrafts().test();

        schedulerProvider.triggerActions();
        testObserver.assertError(throwable);

    }

    @Test
    public void creditDraftRawItemsFromServiceAreMapped() throws Exception {
        List<CreditDraftRaw> rawList = createRawList();
        new ArrangeBuilder().withCreditDraftsFromService(rawList)
                            .withMappedCredit(Mockito.mock(CreditDraft.class));

        repository.fetchCreditDrafts().subscribe();

        schedulerProvider.triggerActions();

        Mockito.verify(mapper).apply(rawList.get(0));
        Mockito.verify(mapper).apply(rawList.get(1));
        Mockito.verify(mapper).apply(rawList.get(2));
    }

    @Test
    public void creditDraftsAreStoredInStoreViaReplaceAll() throws Exception {
        CreditDraft safe = Mockito.mock(CreditDraft.class);
        new ArrangeBuilder().withCreditDraftsFromService(Collections.singletonList(Mockito.mock(CreditDraftRaw.class)))
                            .withMappedCredit(safe);

        repository.fetchCreditDrafts().subscribe();

        schedulerProvider.triggerActions();

        Mockito.verify(store).replaceAll(Collections.singletonList(safe));
    }

    private static List<CreditDraftRaw> createRawList() {
        return new ArrayList<CreditDraftRaw>() {{
            add(Mockito.mock(CreditDraftRaw.class));
            add(Mockito.mock(CreditDraftRaw.class));
            add(Mockito.mock(CreditDraftRaw.class));
        }};
    }

    private class ArrangeBuilder {

        private ArrangeBuilder withObservableFromStore(Observable<Option<List<CreditDraft>>> observable) {
            Mockito.when(store.getAll()).thenReturn(observable);
            return this;
        }

        private ArrangeBuilder withCreditDraftsFromService(List<CreditDraftRaw> raws) {
            Mockito.when(service.getCreditDrafts()).thenReturn(Single.just(raws));
            return this;
        }

        private ArrangeBuilder withErrorInCreditDraftsFromService(Throwable error) {
            Mockito.when(service.getCreditDrafts()).thenReturn(Single.error(error));
            return this;
        }

        private ArrangeBuilder withMappedCredit(CreditDraft draft) throws Exception {
            Mockito.when(mapper.apply(Mockito.any(CreditDraftRaw.class))).thenReturn(draft);
            return this;
        }
    }
}
