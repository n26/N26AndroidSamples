package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.n26.n26androidsamples.base.data.store.ReactiveStore;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import polanski.option.Option;

@Singleton
public class CreditRepository {

    @NonNull
    private final ReactiveStore<String, CreditDraft> store;

    @NonNull
    private final CreditService creditService;

    @NonNull
    private final CreditDraftMapper creditDraftMapper;

    @Inject
    CreditRepository(@NonNull final ReactiveStore<String, CreditDraft> store,
                     @NonNull final CreditService creditService,
                     @NonNull final CreditDraftMapper creditDraftMapper) {
        this.store = store;
        this.creditService = creditService;
        this.creditDraftMapper = creditDraftMapper;
    }

    @NonNull
    public Flowable<Option<List<CreditDraft>>> getAllCreditDrafts() {
        return store.getAll();
    }

    @NonNull
    public Completable fetchCreditDrafts() {
        return creditService.getCreditDrafts()
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.computation())
                            .flatMapObservable(Observable::fromIterable)
                            // map from raw to safe
                            .map(creditDraftMapper)
                            .toList()
                            // put mapped objects in store
                            .doOnSuccess(store::replaceAll)
                            .toCompletable();
    }
}
