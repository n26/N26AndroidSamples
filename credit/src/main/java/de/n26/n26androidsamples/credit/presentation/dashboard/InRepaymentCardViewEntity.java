package de.n26.n26androidsamples.credit.presentation.dashboard;

import com.google.auto.value.AutoValue;

import android.support.annotation.NonNull;

@AutoValue
abstract class InRepaymentCardViewEntity {

    @NonNull
    abstract String id();

    @NonNull
    abstract String title();

    @NonNull
    abstract String formattedAmount();

    @NonNull
    abstract String formattedPaidOutDate();

    @NonNull
    abstract String formattedTotalRepaid();

    @NonNull
    abstract String nextPaymentLabel();

    @NonNull
    abstract String formattedNextPayment();

    static Builder builder() {
        return new AutoValue_InRepaymentCardViewModel.Builder();
    }

    @AutoValue.Builder
    interface Builder {

        Builder id(String id);

        Builder title(String title);

        Builder formattedAmount(String formattedAmount);

        Builder formattedPaidOutDate(String formattedPaidOutDate);

        Builder formattedTotalRepaid(String formattedTotalRepaid);

        Builder nextPaymentLabel(String nextPaymentLabel);

        Builder formattedNextPayment(String formattedNextPayment);

        InRepaymentCardViewEntity build();
    }
}