package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class CreditDraftSummaryRaw {

    @Nullable
    abstract String id();

    @Nullable
    abstract String status();

    abstract double amount();

    abstract int purposeId();

    @Nullable
    abstract String purposeName();

    @Nullable
    abstract String imageUrl();

    abstract int dayOfMonth();

    @Nullable
    abstract String updated();

    @Nullable
    abstract CreditRepaymentInfoRaw repaymentInfo();

    @NonNull
    public static TypeAdapter<CreditDraftSummaryRaw> typeAdapter(@NonNull final Gson gson) {
        return new AutoValue_CreditDraftSummaryRaw.GsonTypeAdapter(gson);
    }

    @NonNull
    public static Builder builder() {
        return new AutoValue_CreditDraftSummaryRaw.Builder();
    }

    @AutoValue.Builder
    interface Builder {

        Builder id(final String id);

        Builder status(final String status);

        Builder amount(final double amount);

        Builder purposeId(final int purposeId);

        Builder purposeName(final String purposeName);

        Builder imageUrl(final String imageUrl);

        Builder dayOfMonth(final int dayOfMonth);

        Builder updated(final String updated);

        Builder repaymentInfo(final CreditRepaymentInfoRaw repaymentInfo);

        CreditDraftSummaryRaw build();
    }
}
