package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class CreditRepaymentInfoRaw {

    @Nullable
    abstract String disbursedDate();

    abstract double nextPayment();

    @Nullable
    abstract String nextPaymentDate();

    abstract int currentInstalment();

    abstract int totalInstalments();

    abstract double totalToRepay();

    abstract double totalRemaining();

    abstract double totalPaid();

    @NonNull
    public static TypeAdapter<CreditRepaymentInfoRaw> typeAdapter(@NonNull final Gson gson) {
        return new AutoValue_CreditRepaymentInfoRaw.GsonTypeAdapter(gson);
    }

    @NonNull
    static Builder builder() {
        return new AutoValue_CreditRepaymentInfoRaw.Builder();
    }

    @AutoValue.Builder
    interface Builder {

        Builder disbursedDate(String disbursedDate);

        Builder nextPayment(double nextPayment);

        Builder nextPaymentDate(String nextPaymentDate);

        Builder currentInstalment(int currentInstalment);

        Builder totalInstalments(int totalInstalments);

        Builder totalToRepay(double totalToRepay);

        Builder totalRemaining(double totalRemaining);

        Builder totalPaid(double totalPaid);

        CreditRepaymentInfoRaw build();
    }
}
