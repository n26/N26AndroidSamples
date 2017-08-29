package de.n26.n26androidsamples.credit.presentation.dashboard;

import org.reactivestreams.Publisher;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.domain.RetrieveCreditDraftList;

import static polanski.option.Option.none;

public class CreditDashboardViewModel extends ViewModel {

    @NonNull
    private final RetrieveCreditDraftList retrieveCreditDraftList;

    @NonNull
    private final CreditDisplayableItemFlatMapper creditDisplayableItemFlatMapper;

    @Inject
    CreditDashboardViewModel(@NonNull final RetrieveCreditDraftList retrieveCreditDraftList,
                             @NonNull final CreditDisplayableItemFlatMapper creditDisplayableItemFlatMapper) {
        this.retrieveCreditDraftList = retrieveCreditDraftList;
        this.creditDisplayableItemFlatMapper = creditDisplayableItemFlatMapper;
    }

    @NonNull
    LiveData<List<DisplayableItem>> getDisplayableItemListLiveData() {
        return LiveDataReactiveStreams.fromPublisher(displayableItemListPublisher());
    }

    @NonNull
    private Publisher<List<DisplayableItem>> displayableItemListPublisher() {
        return retrieveCreditDraftList.getBehaviorStream(none())
                                      .flatMapSingle(creditDisplayableItemFlatMapper);
    }
}
