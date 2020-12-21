package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.common.rx.SchedulerProvider;
import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.domain.RetrieveCreditDraftList;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static polanski.option.Option.none;

public class CreditDashboardViewModel extends ViewModel {

    private static final String TAG = CreditDashboardViewModel.class.getSimpleName();

    @NonNull
    private final RetrieveCreditDraftList retrieveCreditDraftList;

    @NonNull
    private final CreditDisplayableItemMapper creditDisplayableItemMapper;

    @NonNull
    private final MutableLiveData<List<DisplayableItem>> creditListLiveData = new MutableLiveData<>();

    @NotNull
    private final CompositeDisposable compositeDisposable;

    @NotNull
    private final SchedulerProvider schedulerProvider;

    @Inject
    CreditDashboardViewModel(@NonNull final RetrieveCreditDraftList retrieveCreditDraftList,
                             @NonNull final CreditDisplayableItemMapper creditDisplayableItemMapper,
                             @NotNull final CompositeDisposable compositeDisposable,
                             @NotNull final SchedulerProvider schedulerProvider) {
        this.retrieveCreditDraftList = retrieveCreditDraftList;
        this.creditDisplayableItemMapper = creditDisplayableItemMapper;
        this.compositeDisposable = compositeDisposable;
        this.schedulerProvider = schedulerProvider;
        // Bind view model
        this.compositeDisposable.add(bindToCreditDrafts());
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
                .observeOn(schedulerProvider.computation())
                .map(creditDisplayableItemMapper)
                .subscribe(creditListLiveData::postValue,
                        e -> Log.e(TAG, "Error updating credit list live data", e));
    }
}
