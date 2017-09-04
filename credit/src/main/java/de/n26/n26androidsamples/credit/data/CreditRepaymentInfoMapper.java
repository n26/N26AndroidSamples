package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;

import de.n26.n26androidsamples.base.data.EssentialParamMissingException;

final class CreditRepaymentInfoMapper {

    @NonNull
    @SuppressWarnings("ConstantConditions")
    static CreditRepaymentInfo processRaw(@NonNull final CreditRepaymentInfoRaw raw) {
        assertEssentialParams(raw);

        return CreditRepaymentInfo.builder()
                                  .disbursedDate(raw.disbursedDate())
                                  .nextPaymentDate(raw.nextPaymentDate())
                                  .nextPayment(raw.nextPayment())
                                  .totalPaid(raw.totalPaid())
                                  .build();
    }

    private static void assertEssentialParams(@NonNull final CreditRepaymentInfoRaw raw) {
        String missingParams = "";

        if (raw.disbursedDate() == null || raw.disbursedDate().isEmpty()) {
            missingParams += "disbursedDate";
        }

        if (raw.nextPaymentDate() == null || raw.nextPaymentDate().isEmpty()) {
            missingParams += " nextPaymentDate";
        }

        if (!missingParams.isEmpty()) {
            throw new EssentialParamMissingException(missingParams, raw);
        }
    }
}
