package de.n26.n26androidsamples.credit.data;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

interface CreditService {

    @GET("credit/drafts")
    Single<List<CreditDraftRaw>> getCreditDrafts();
}
