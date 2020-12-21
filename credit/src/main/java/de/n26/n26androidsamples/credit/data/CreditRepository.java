package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.n26.n26androidsamples.base.common.rx.SchedulerProvider;
import de.n26.n26androidsamples.base.data.store.ReactiveStore;
import io.reactivex.Completable;
import io.reactivex.Observable;
import polanski.option.Option;

@Singleton
public class CreditRepository {

    @NonNull
    private final ReactiveStore<String, CreditDraft> store;

    @NonNull
    private final CreditService creditService;

    @NonNull
    private final CreditDraftMapper creditDraftMapper;

    @NonNull
    private final SchedulerProvider schedulerProvider;

    @Inject
    CreditRepository(@NonNull final ReactiveStore<String, CreditDraft> store,
                     @NonNull final CreditService creditService,
                     @NonNull final CreditDraftMapper creditDraftMapper,
                     @NotNull final SchedulerProvider schedulerProvider) {
        this.store = store;
        this.creditService = creditService;
        this.creditDraftMapper = creditDraftMapper;
        this.schedulerProvider = schedulerProvider;
    }

    @NonNull
    public Observable<Option<List<CreditDraft>>> getAllCreditDrafts() {
        return store.getAll();
    }

    @NonNull
    public Completable fetchCreditDrafts() {
        return creditService.getCreditDrafts()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.computation())
                .flatMapObservable(Observable::fromIterable)
                // map from raw to safe
                .map(creditDraftMapper)
                .toList()
                // put mapped objects in store
                .doOnSuccess(store::replaceAll)
                .toCompletable();
    }
}
