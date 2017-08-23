package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.n26.n26androidsamples.base.data.store.ReactiveStore;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import polanski.option.Option;

@Singleton
public class CreditRepository {

    @NonNull
    private final ReactiveStore<String, CreditDraftSummary> store;

    @NonNull
    private final CreditService creditService;

    @Inject
    CreditRepository(@NonNull final ReactiveStore<String, CreditDraftSummary> store,
                     @NonNull final CreditService creditService) {
        this.store = store;
        this.creditService = creditService;
    }

    @NonNull
    public Flowable<Option<List<CreditDraftSummary>>> getCreditDraftSummaryListBehaviorStream() {
        return store.getAllBehaviorStream();
    }

    @NonNull
    public Completable fetchCreditDraftSummariesSingle() {
        return creditService.getDraftSummaryList()
                            .map(CreditDraftSummaryMapper::processList)
                            .doOnSuccess(store::replaceAll)
                            .toCompletable()
                            .subscribeOn(Schedulers.computation());
    }
}
