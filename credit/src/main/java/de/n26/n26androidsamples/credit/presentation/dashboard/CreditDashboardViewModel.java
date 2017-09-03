package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.injection.scopes.FragmentScope;
import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.domain.RetrieveCreditDraftList;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static polanski.option.Option.none;

@FragmentScope
public class CreditDashboardViewModel extends ViewModel {

    @NonNull
    private final RetrieveCreditDraftList retrieveCreditDraftList;

    @NonNull
    private final CreditDisplayableItemFlatMapper creditDisplayableItemFlatMapper;

    @NonNull
    private final MutableLiveData<List<DisplayableItem>> creditListLiveData = new MutableLiveData<>();

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    CreditDashboardViewModel(
            @NonNull final RetrieveCreditDraftList retrieveCreditDraftList,
            @NonNull final CreditDisplayableItemFlatMapper creditDisplayableItemFlatMapper) {
        this.retrieveCreditDraftList = retrieveCreditDraftList;
        this.creditDisplayableItemFlatMapper = creditDisplayableItemFlatMapper;
        // Bind view model
        compositeDisposable.add(bindToCreditDrafts());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    @NonNull
    LiveData<List<DisplayableItem>> getCreditListLiveData() {
        return creditListLiveData;
    }

    @NonNull
    private Disposable bindToCreditDrafts() {
        return retrieveCreditDraftList.getBehaviorStream(none())
                                      .observeOn(Schedulers.computation())
                                      .flatMapSingle(creditDisplayableItemFlatMapper)
                                      .subscribe(creditListLiveData::postValue,
                                                 e -> Timber.e(e, "Error updating credit list live data"));
    }
}
