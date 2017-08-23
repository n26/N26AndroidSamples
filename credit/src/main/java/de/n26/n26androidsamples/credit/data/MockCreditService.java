package de.n26.n26androidsamples.credit.data;

import com.google.gson.Gson;

import android.support.annotation.NonNull;

import java.util.Collections;
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
        return Single.just(Collections.singletonList(creditDraftSummaryRawTestBuilder().status("IN_REPAYMENT").build()));
    }

    static CreditDraftSummaryRaw.Builder creditDraftSummaryRawTestBuilder() {
        return CreditDraftSummaryRaw.builder()
                                    .amount(0.0)
                                    .id("ID")
                                    .imageUrl("imageUrl")
                                    .dayOfMonth(1)
                                    .purposeId(2)
                                    .purposeName("purpose")
                                    .status("status")
                                    .updated("date")
                                    .repaymentInfo(creditRepaymentInfoRawTestBuilder().build());
    }

    static CreditRepaymentInfoRaw.Builder creditRepaymentInfoRawTestBuilder() {
        return CreditRepaymentInfoRaw.builder()
                                     .disbursedDate("2017-08-07T17:53:55.07+02:00")
                                     .nextPaymentDate("2017-08-07T17:53:55.07+02:00")
                                     .nextPayment(400.0)
                                     .currentInstalment(5)
                                     .totalInstalments(10)
                                     .totalToRepay(10000d)
                                     .totalRemaining(5984.74)
                                     .totalPaid(4015.26);
    }
}
