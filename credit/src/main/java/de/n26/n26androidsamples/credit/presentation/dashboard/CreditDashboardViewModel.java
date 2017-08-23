package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.domain.GetCreditDraftSummaryList;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem.toDisplayableItem;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;
import static polanski.option.Option.none;

public class CreditDashboardViewModel extends ViewModel {

    @NonNull
    private final CompositeDisposable d;

    @NonNull
    private final MutableLiveData<List<DisplayableItem>> displayableItemListLiveData;

    @NonNull
    private final GetCreditDraftSummaryList getCreditDraftSummaryList;

    @NonNull
    private final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper;

    public CreditDashboardViewModel(@NonNull final GetCreditDraftSummaryList getCreditDraftSummaryList,
                                    @NonNull final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper) {
        this.getCreditDraftSummaryList = getCreditDraftSummaryList;
        this.inRepaymentCardViewEntityMapper = inRepaymentCardViewEntityMapper;
        this.d = new CompositeDisposable();
        this.displayableItemListLiveData = new MutableLiveData<>();

        bindToData();
    }

    private void bindToData() {
        d.add(getCreditDraftSummaryList.getBehaviorStream(none())
                                       .flatMapSingle(draftList -> Flowable.fromIterable(draftList)
                                                                           .map(inRepaymentCardViewEntityMapper)
                                                                           .map(viewEntity -> toDisplayableItem(viewEntity, IN_REPAYMENT))
                                                                           .toList())
                                       .subscribe(displayableItemListLiveData::setValue,
                                                  e -> Timber.e("Error getting draft list for credit", e)));
    }

    @NonNull
    public LiveData<List<DisplayableItem>> getDisplayableItemListLiveData() {
        return displayableItemListLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        d.clear();
    }
}
