package de.n26.n26androidsamples.credit.presentation.dashboard;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import java.util.Collections;
import java.util.List;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.data.CreditDraft;
import de.n26.n26androidsamples.credit.domain.RetrieveCreditDraftList;
import de.n26.n26androidsamples.credit.test_common.BaseTest;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import polanski.option.Option;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreditDashboardViewModelTest extends BaseTest {

    @Rule
    public InstantTaskExecutorRule archComponentsRule;

    @Mock
    private RetrieveCreditDraftList interactor;

    @Mock
    private CreditDisplayableItemMapper mapper;

    private CreditDashboardViewModel viewModel;

    private ArrangeBuilder arrangeBuilder;

    @Before
    public void setUp() {
        arrangeBuilder = new ArrangeBuilder();
        viewModel = new CreditDashboardViewModel(interactor, mapper);
    }

    @Test
    public void creditDraftsFromInteractorAreMapped() throws Exception {
        List<DisplayableItem> displayableItems = Collections.singletonList(Mockito.mock(DisplayableItem.class));
        List<CreditDraft> creditDrafts = Collections.singletonList(Mockito.mock(CreditDraft.class));

        arrangeBuilder.withMappedDisplayableItems(displayableItems)
                      .interactorEmitsCreditDrafts(creditDrafts);

        Mockito.verify(mapper).apply(creditDrafts);
    }

    @Ignore
    @Test
    public void displayableItemsGoIntoLiveDataWhenInteractorEmitsCreditDrafts() throws Exception {
        List<DisplayableItem> displayableItems = Collections.singletonList(Mockito.mock(DisplayableItem.class));
        List<CreditDraft> creditDrafts = Collections.singletonList(Mockito.mock(CreditDraft.class));

        arrangeBuilder.withMappedDisplayableItems(displayableItems)
                      .interactorEmitsCreditDrafts(creditDrafts);

        // FIXME: The LiveData is not receiving the value! Figure out how to unit test LiveData.
        assertThat(viewModel.getCreditListLiveData().getValue()).isEqualTo(displayableItems);
    }

    private class ArrangeBuilder {

        FlowableProcessor<List<CreditDraft>> interactorFlowable = BehaviorProcessor.create();

        private ArrangeBuilder() {
            Mockito.when(interactor.getBehaviorStream(Option.none())).thenReturn(interactorFlowable);
        }

        private ArrangeBuilder interactorEmitsCreditDrafts(List<CreditDraft> creditDrafts) {
            interactorFlowable.onNext(creditDrafts);
            return this;
        }

        private ArrangeBuilder withMappedDisplayableItems(List<DisplayableItem> displayableItems) throws Exception {
            Mockito.when(mapper.apply(Mockito.anyList())).thenReturn(displayableItems);
            return this;
        }
    }
}