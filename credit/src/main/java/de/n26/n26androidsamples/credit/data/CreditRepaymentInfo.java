package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CreditRepaymentInfo {

    @NonNull
    public abstract String disbursedDate(); // iso 8601 date

    @NonNull
    public abstract String nextPaymentDate(); // iso 8601 date

    public abstract double totalPaid();

    public abstract double nextPayment();

    public static Builder builder() {
        return new AutoValue_CreditRepaymentInfo.Builder();
    }

    @AutoValue.Builder
    public interface Builder {

        Builder disbursedDate(@NonNull String disbursedDate);

        Builder nextPaymentDate(@NonNull String nextPaymentDate);

        Builder totalPaid(double totalPaid);

        Builder nextPayment(double nextPayment);

        CreditRepaymentInfo build();
    }
}
