package de.n26.n26androidsamples.credit.data;

import polanski.option.Option;

import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.UNEXPECTED;

public class CreditDataTestUtils {

    static CreditDraftRaw.Builder creditDraftRawTestBuilder() {
        return CreditDraftRaw.builder()
                             .amount(0.0)
                             .id("ID")
                             .imageUrl("imageUrl")
                             .dayOfMonth(1)
                             .purposeId(2)
                             .purposeName("purpose")
                             .status("status")
                             .updated("date");
    }

    public static CreditDraft.Builder creditDraftTestBuilder() {
        return CreditDraft.builder()
                          .id("ID")
                          .purpose("PURPOSE")
                          .purposeId(5)
                          .status(UNEXPECTED)
                          .amount(0.0)
                          .imageUrl("imageUrl")
                          .creditRepaymentInfo(Option.ofObj(creditRepaymentInfoTestBuilder().build()));
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

    public static CreditRepaymentInfo.Builder creditRepaymentInfoTestBuilder() {
        return CreditRepaymentInfo.builder()
                                  .disbursedDate("2017-08-07T17:53:55.07+02:00")
                                  .nextPaymentDate("2017-08-07T17:53:55.07+02:00")
                                  .nextPayment(400.0)
                                  .totalPaid(4015.26);
    }
}
