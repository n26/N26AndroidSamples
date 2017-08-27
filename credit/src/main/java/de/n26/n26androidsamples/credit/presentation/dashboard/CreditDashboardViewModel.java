package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.domain.RetrieveCreditDraftSummaryList;
import io.reactivex.Flowable;

import static de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem.toDisplayableItem;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;
import static polanski.option.Option.none;

public class CreditDashboardViewModel extends ViewModel {

    @NonNull
    private final RetrieveCreditDraftSummaryList retrieveCreditDraftSummaryList;

    @NonNull
    private final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper;

    @Inject
    CreditDashboardViewModel(@NonNull final RetrieveCreditDraftSummaryList retrieveCreditDraftSummaryList,
                             @NonNull final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper) {
        this.retrieveCreditDraftSummaryList = retrieveCreditDraftSummaryList;
        this.inRepaymentCardViewEntityMapper = inRepaymentCardViewEntityMapper;
    }

    @NonNull
    LiveData<List<DisplayableItem>> getDisplayableItemListLiveData() {
        return LiveDataReactiveStreams.fromPublisher(displayableItemListPublisher());
    }

    @NonNull
    private Publisher<List<DisplayableItem>> displayableItemListPublisher() {
        return retrieveCreditDraftSummaryList.getBehaviorStream(none())
                                             .flatMapSingle(draftList -> Flowable.fromIterable(draftList)
                                                                                 .map(inRepaymentCardViewEntityMapper)
                                                                                 .map(viewEntity -> toDisplayableItem(viewEntity, IN_REPAYMENT))
                                                                                 .toList());
    }
}
