package de.n26.n26androidsamples.credit.data;


import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Single;

class MockCreditService implements CreditService {

    @NonNull
    private final Gson gson;

    MockCreditService(@NonNull final Gson gson) {
        this.gson = gson;
    }

    @Override
    public Single<List<CreditDraftSummaryRaw>> getDraftSummaryList() {
        return Single.error(new IllegalStateException("Not implemented yet"));
    }
}
