package de.n26.n26androidsamples.credit.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.n26.n26androidsamples.credit.data.CreditDataTestUtils;
import de.n26.n26androidsamples.credit.data.CreditDraft;
import de.n26.n26androidsamples.credit.data.CreditRepository;
import de.n26.n26androidsamples.credit.test_common.BaseTest;
import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subjects.BehaviorSubject;
import polanski.option.Option;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RetrieveCreditDraftListTest extends BaseTest {

    @Mock
    private CreditRepository creditRepository;

    private RetrieveCreditDraftList interactor;

    private ArrangeBuilder arrangeBuilder;

    private TestObserver<List<CreditDraft>> ts;

    @Before
    public void setUp() {
        interactor = new RetrieveCreditDraftList(creditRepository);
        arrangeBuilder = new ArrangeBuilder();
        ts = new TestObserver<>();
    }

    @Test
    public void draftsFromRepoAreUnwrappedAndPassedOn() {
        // Arrange
        List<CreditDraft> testDraftList = createTestDraftList();
        interactor.getBehaviorStream(Option.none()).subscribe(ts);

        // Act
        arrangeBuilder.emitDraftListFromRepo(Option.ofObj(testDraftList));

        // Assert
        ts.assertNotTerminated();
        ts.assertValue(testDraftList);
    }

    @Test
    public void whenRepoIsEmptyFetchAndNoEmissions() {
        // Arrange
        arrangeBuilder.emitDraftListFromRepo(Option.none())
                      .withSuccessfulFetch();

        // Act
        interactor.getBehaviorStream(Option.none()).subscribe(ts);

        // Assert
        verify(creditRepository).fetchCreditDrafts();
        ts.assertNoValues();
        ts.assertNotTerminated();
    }

    @Test
    public void propagateFetchError() {
        // Arrange
        Throwable throwable = Mockito.mock(Throwable.class);
        arrangeBuilder.emitDraftListFromRepo(Option.none())
                      .withFetchError(throwable);

        // Act
        interactor.getBehaviorStream(Option.none()).subscribe(ts);

        // Assert
        verify(creditRepository).fetchCreditDrafts();
        ts.assertNoValues();
        ts.assertError(throwable);
    }

    private List<CreditDraft> createTestDraftList() {
        return new ArrayList<CreditDraft>() {{
            add(CreditDataTestUtils.creditDraftTestBuilder().id("1").build());
            add(CreditDataTestUtils.creditDraftTestBuilder().id("2").build());
            add(CreditDataTestUtils.creditDraftTestBuilder().id("3").build());
        }};
    }

    private class ArrangeBuilder {

        private BehaviorSubject<Option<List<CreditDraft>>> repoCreditDraftStream = BehaviorSubject.create();

        private ArrangeBuilder() {
            when(creditRepository.getAllCreditDrafts()).thenReturn(repoCreditDraftStream);
        }

        private ArrangeBuilder emitDraftListFromRepo(@NonNull final Option<List<CreditDraft>> listOption) {
            repoCreditDraftStream.onNext(listOption);
            return this;
        }

        private ArrangeBuilder withSuccessfulFetch() {
            when(creditRepository.fetchCreditDrafts()).thenReturn(Completable.complete());
            return this;
        }

        private ArrangeBuilder withFetchError(@NonNull final Throwable throwable) {
            when(creditRepository.fetchCreditDrafts()).thenReturn(Completable.error(throwable));
            return this;
        }
    }
}
