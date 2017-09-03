package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.common.utils.CurrencyUtils;
import de.n26.n26androidsamples.base.common.utils.TimeUtils;
import de.n26.n26androidsamples.base.presentation.providers.StringProvider;
import de.n26.n26androidsamples.credit.R;
import de.n26.n26androidsamples.credit.data.CreditDraft;
import de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus;
import de.n26.n26androidsamples.credit.data.CreditRepaymentInfo;
import io.reactivex.functions.Function;

import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.IN_REPAYMENT;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DateFormats.DASHBOARD_DATE_FORMAT_NEXT_PAYMENT;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DateFormats.DASHBOARD_DATE_FORMAT_PAID_OUT;
import static polanski.option.OptionUnsafe.orThrowUnsafe;

class InRepaymentCardViewEntityMapper implements Function<CreditDraft, InRepaymentCardViewEntity> {

    private static final String TAG = InRepaymentCardViewEntityMapper.class.getSimpleName();

    @NonNull
    private final StringProvider stringProvider;

    @NonNull
    private final TimeUtils timeUtils;

    @NonNull
    private final CurrencyUtils currencyUtils;

    @Inject
    InRepaymentCardViewEntityMapper(@NonNull final StringProvider stringProvider,
                                    @NonNull final TimeUtils timeUtils,
                                    @NonNull final CurrencyUtils currencyUtils) {
        this.stringProvider = stringProvider;
        this.timeUtils = timeUtils;
        this.currencyUtils = currencyUtils;
    }

    @NonNull
    public InRepaymentCardViewEntity apply(@NonNull final CreditDraft draft) throws Exception {
        assertCorrectStatus(draft.status());

        final CreditRepaymentInfo repaymentInfo = orThrowUnsafe(draft.creditRepaymentInfo(),
                                                                new IllegalStateException(TAG + ": RepaymentInformation missing."));
        return InRepaymentCardViewEntity.builder()
                                        .id(draft.id())
                                        .title(draft.purpose())
                                        .formattedAmount(currencyUtils.formatAmount(draft.amount()))
                                        .nextPaymentLabel(mapToNextPaymentLabel(repaymentInfo.totalPaid()))
                                        .formattedPaidOutDate(mapToFormattedPaidOutDate(repaymentInfo.disbursedDate()))
                                        .formattedTotalRepaid(currencyUtils.formatAmount(repaymentInfo.totalPaid()))
                                        .formattedNextPayment(mapToNextPaymentInfo(repaymentInfo.nextPayment(), repaymentInfo.nextPaymentDate()))
                                        .build();

    }

    @SuppressWarnings("unchecked")
    @NonNull
    private String mapToFormattedPaidOutDate(@NonNull final String paidOutDate) {
        final String formattedPaidOutDate = timeUtils.formatDateString(paidOutDate, DASHBOARD_DATE_FORMAT_PAID_OUT);
        return stringProvider.getStringAndApplySubstitutions(R.string.credit_dashboard_repayment_paid_out,
                                                             Pair.create("date", formattedPaidOutDate));
    }

    @NonNull
    private String mapToNextPaymentLabel(final double totalPaid) {
        return totalPaid > 0
                ? stringProvider.getString(R.string.credit_dashboard_repayment_payment_next)
                : stringProvider.getString(R.string.credit_dashboard_repayment_payment_first);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    private String mapToNextPaymentInfo(final double nextPayment, @NonNull final String nextPaymentDate) {
        final String formattedNextPayment = currencyUtils.formatAmount(nextPayment);
        final String formattedNextPaymentDate = timeUtils.formatDateString(nextPaymentDate, DASHBOARD_DATE_FORMAT_NEXT_PAYMENT);
        return stringProvider.getStringAndApplySubstitutions(R.string.credit_dashboard_repayment_text,
                                                             Pair.create("amount", formattedNextPayment),
                                                             Pair.create("date", formattedNextPaymentDate));
    }

    private static void assertCorrectStatus(@NonNull final CreditDraftStatus draftStatus) {
        if (draftStatus != IN_REPAYMENT) {
            throw new IllegalStateException("Invalid draft status for in repayment card: " + draftStatus);
        }
    }
}
