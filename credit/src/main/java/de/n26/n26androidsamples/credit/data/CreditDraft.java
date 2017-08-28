package de.n26.n26androidsamples.credit.data;

import com.google.auto.value.AutoValue;

import android.support.annotation.NonNull;

import polanski.option.Option;

@AutoValue
public abstract class CreditDraft {

    public enum CreditDraftStatus {
        INITIALISED,
        PENDING_PROVIDER_APPROVAL,
        PENDING_ESIGN,
        WAITING_FOR_DISBURSEMENT,
        IN_REPAYMENT,
        CONTRACT_PROCESSING,
        ADDITIONAL_ACCOUNT_REQUIRED,
        UNEXPECTED
    }

    @NonNull
    public abstract String id();

    @NonNull
    public abstract String purpose();

    public abstract int purposeId();

    @NonNull
    public abstract String imageUrl();

    @NonNull
    public abstract CreditDraftStatus status();

    public abstract double amount();

    @NonNull
    public abstract Option<CreditRepaymentInfo> creditRepaymentInfo();

    @NonNull
    public static Builder builder() {
        return new AutoValue_CreditDraft.Builder();
    }

    @AutoValue.Builder
    public interface Builder {

        Builder id(final String id);

        Builder purpose(final String purpose);

        Builder purposeId(final int purposeId);

        Builder imageUrl(final String imageUrl);

        Builder status(final CreditDraftStatus status);

        Builder amount(final double amount);

        Builder creditRepaymentInfo(final Option<CreditRepaymentInfo> creditRepaymentInfo);

        CreditDraft build();
    }
}
