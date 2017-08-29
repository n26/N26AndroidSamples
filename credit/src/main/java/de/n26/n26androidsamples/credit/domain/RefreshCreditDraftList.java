package de.n26.n26androidsamples.credit.domain;

import android.support.annotation.NonNull;

import de.n26.n26androidsamples.base.domain.ReactiveInteractor.RefreshInteractor;
import de.n26.n26androidsamples.credit.data.CreditRepository;
import io.reactivex.Completable;
import polanski.option.Option;

public class RefreshCreditDraftList implements RefreshInteractor<Void> {

    @NonNull
    private final CreditRepository creditRepository;

    public RefreshCreditDraftList(@NonNull final CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @NonNull
    @Override
    public Completable getRefreshSingle(@NonNull final Option<Void> params) {
        return creditRepository.fetchCreditDrafts();
    }
}
