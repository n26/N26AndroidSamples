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
import io.reactivex.Flowable;
import io.reactivex.Single;
import polanski.option.Option;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreditRepositoryTest extends BaseTest {

    @Mock
    private ReactiveStore<String, CreditDraft> store;

    @Mock
    private CreditService service;

    @Mock
    private CreditDraftMapper mapper;

    private CreditRepository repository;

    @Before
    public void setUp() {
        repository = new CreditRepository(store, service, mapper);
    }

    @Test
    public void getAllCreditDraftsReturnsStoreFlowable() {
        Flowable<Option<List<CreditDraft>>> storeFlowable = Flowable.empty();
        new ArrangeBuilder().withFlowableFromStore(storeFlowable);

        assertThat(repository.getAllCreditDrafts()).isEqualTo(storeFlowable);
    }

    @Test
    public void fetchCreditDraftsEmitsErrorWhenNetworkServiceErrors() {
        Throwable throwable = Mockito.mock(Throwable.class);
        new ArrangeBuilder().withErrorInCreditDraftsFromService(throwable);

        repository.fetchCreditDrafts().test().assertError(throwable);
    }

    @Test
    public void creditDraftRawItemsFromServiceAreMapped() throws Exception {
        List<CreditDraftRaw> rawList = createRawList();
        new ArrangeBuilder().withCreditDraftsFromService(rawList)
                            .withMappedCredit(Mockito.mock(CreditDraft.class));

        repository.fetchCreditDrafts().subscribe();

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

        private ArrangeBuilder withFlowableFromStore(Flowable<Option<List<CreditDraft>>> flowable) {
            Mockito.when(store.getAll()).thenReturn(flowable);
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