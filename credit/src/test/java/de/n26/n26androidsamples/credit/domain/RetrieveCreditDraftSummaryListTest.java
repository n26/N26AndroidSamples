package de.n26.n26androidsamples.credit.domain;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import de.n26.n26androidsamples.credit.BaseTest;
import de.n26.n26androidsamples.credit.data.CreditDataTestUtils;
import de.n26.n26androidsamples.credit.data.CreditDraftSummary;
import de.n26.n26androidsamples.credit.data.CreditRepository;
import io.reactivex.Completable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.subscribers.TestSubscriber;
import polanski.option.Option;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RetrieveCreditDraftSummaryListTest extends BaseTest {

    @Mock
    private CreditRepository creditRepository;

    private RetrieveCreditDraftSummaryList interactor;

    private ArrangeBuilder arrangeBuilder;

    private TestSubscriber<List<CreditDraftSummary>> ts;

    @Before
    public void setUp() {
        interactor = new RetrieveCreditDraftSummaryList(creditRepository);
        arrangeBuilder = new ArrangeBuilder();
        ts = new TestSubscriber<>();
    }

    @Test
    public void draftsFromRepoAreUnwrappedAndPassedOn() {
        // Arrange
        List<CreditDraftSummary> testDraftList = createTestDraftList();
        interactor.getBehaviorStream(Option.none()).subscribe(ts);

        // Act
        arrangeBuilder.emitDraftSummaryListFromRepo(Option.ofObj(testDraftList));

        // Assert
        ts.assertNoErrors();
        ts.assertValue(testDraftList);
    }

    @Test
    public void whenRepoIsEmptyFetchAndNoEmissions() {
        // Arrange
        arrangeBuilder.emitDraftSummaryListFromRepo(Option.none())
                      .withSuccessfulFetch();

        // Act
        interactor.getBehaviorStream(Option.none()).subscribe(ts);

        // Assert
        verify(creditRepository).fetchCreditDraftSummariesSingle();
        ts.assertNoValues();
        ts.assertNoErrors();
    }

    @Test
    public void propagateFetchError() {
        // Arrange
        Throwable throwable = Mockito.mock(Throwable.class);
        arrangeBuilder.emitDraftSummaryListFromRepo(Option.none())
                      .withFetchError(throwable);

        // Act
        interactor.getBehaviorStream(Option.none()).subscribe(ts);

        // Assert
        verify(creditRepository).fetchCreditDraftSummariesSingle();
        ts.assertNoValues();
        ts.assertError(throwable);
    }

    private List<CreditDraftSummary> createTestDraftList() {
        return new ArrayList<CreditDraftSummary>() {{
            add(CreditDataTestUtils.creditDraftSummaryTestBuilder().id("1").build());
            add(CreditDataTestUtils.creditDraftSummaryTestBuilder().id("2").build());
            add(CreditDataTestUtils.creditDraftSummaryTestBuilder().id("3").build());
        }};
    }

    private class ArrangeBuilder {

        private BehaviorProcessor<Option<List<CreditDraftSummary>>> repoDraftStream = BehaviorProcessor.create();

        private ArrangeBuilder() {
            when(creditRepository.getCreditDraftSummaryListBehaviorStream()).thenReturn(repoDraftStream);
        }

        private ArrangeBuilder emitDraftSummaryListFromRepo(@NonNull final Option<List<CreditDraftSummary>> listOption) {
            repoDraftStream.onNext(listOption);
            return this;
        }

        private ArrangeBuilder withSuccessfulFetch() {
            when(creditRepository.fetchCreditDraftSummariesSingle()).thenReturn(Completable.complete());
            return this;
        }

        private ArrangeBuilder withFetchError(@NonNull final Throwable throwable) {
            when(creditRepository.fetchCreditDraftSummariesSingle()).thenReturn(Completable.error(throwable));
            return this;
        }
    }
}
