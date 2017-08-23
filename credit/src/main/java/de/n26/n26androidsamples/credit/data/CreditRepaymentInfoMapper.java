package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;
import android.text.TextUtils;

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

        if (TextUtils.isEmpty(raw.disbursedDate())) {
            missingParams += "disbursedDate";
        }

        if (TextUtils.isEmpty(raw.nextPaymentDate())) {
            missingParams += " nextPaymentDate";
        }

        if (!missingParams.isEmpty()) {
            throw new EssentialParamMissingException(missingParams, raw);
        }
    }
}
