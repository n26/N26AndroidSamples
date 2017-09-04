package de.n26.n26androidsamples.credit.presentation.dashboard;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import android.support.v4.util.Pair;

import de.n26.n26androidsamples.base.common.utils.CurrencyUtils;
import de.n26.n26androidsamples.base.common.utils.TimeUtils;
import de.n26.n26androidsamples.base.presentation.providers.StringProvider;
import de.n26.n26androidsamples.credit.R;
import de.n26.n26androidsamples.credit.data.CreditDataTestUtils;
import de.n26.n26androidsamples.credit.data.CreditDraft;
import de.n26.n26androidsamples.credit.data.CreditRepaymentInfo;
import de.n26.n26androidsamples.credit.test_common.BaseTest;
import polanski.option.Option;

import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.CONTRACT_PROCESSING;
import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.INITIALISED;
import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.IN_REPAYMENT;
import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.PENDING_ESIGN;
import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.PENDING_PROVIDER_APPROVAL;
import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.UNEXPECTED;
import static de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus.WAITING_FOR_DISBURSEMENT;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DateFormats.DASHBOARD_DATE_FORMAT_PAID_OUT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;

public class InRepaymentCardViewEntityMapperTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private StringProvider stringProvider;

    @Mock
    private TimeUtils timeUtils;

    @Mock
    private CurrencyUtils currencyUtils;

    private InRepaymentCardViewEntityMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new InRepaymentCardViewEntityMapper(stringProvider, timeUtils, currencyUtils);
    }

    @Test
    public void mapDraft() throws Exception {
        new ArrangeBuilder().withStringFromProvider("something")
                            .withSubstitutionStringFromProvider("other something")
                            .withFormattedDate("Some date")
                            .withFormattedCurrency("500,00");
        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .id("10")
                                               .purpose("Electronics")
                                               .amount(500)
                                               .status(IN_REPAYMENT)
                                               .build();

        InRepaymentCardViewEntity viewModel = mapper.apply(draft);

        assertThat(viewModel.id()).isEqualTo("10");
        assertThat(viewModel.formattedAmount()).isEqualTo("500,00");
        assertThat(viewModel.title()).isEqualTo("Electronics");
    }

    @Test
    public void missingRepaymentInfoThrows() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("RepaymentInformation missing.");

        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .status(IN_REPAYMENT)
                                               .creditRepaymentInfo(Option.none())
                                               .build();

        mapper.apply(draft);
    }

    @Test
    public void mapRepaymentInfoFormattedPaidOut() throws Exception {
        new ArrangeBuilder().withStringFromProvider("something")
                            .withSubstitutionStringFromProvider("paid out date")
                            .withFormattedDate("Some date")
                            .withFormattedCurrency("500,00");
        CreditRepaymentInfo repaymentInfo = CreditDataTestUtils.creditRepaymentInfoTestBuilder()
                                                               .disbursedDate("2017-08-07T17:53:55.07+02:00")
                                                               .build();
        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .status(IN_REPAYMENT)
                                               .creditRepaymentInfo(Option.ofObj(repaymentInfo))
                                               .build();

        InRepaymentCardViewEntity viewModel = mapper.apply(draft);

        Mockito.verify(timeUtils).formatDateString("2017-08-07T17:53:55.07+02:00", DASHBOARD_DATE_FORMAT_PAID_OUT);
        Mockito.verify(stringProvider).getStringAndApplySubstitutions(
                R.string.credit_dashboard_repayment_paid_out,
                Pair.create("date", "Some date"));
        assertThat(viewModel.formattedPaidOutDate()).isEqualTo("paid out date");
    }

    @Test
    public void mapRepaymentInfoTotalRepaid() throws Exception {
        new ArrangeBuilder().withStringFromProvider("something")
                            .withSubstitutionStringFromProvider("other something")
                            .withFormattedDate("Some date")
                            .withFormattedCurrency("500,00");
        CreditRepaymentInfo repaymentInfo = CreditDataTestUtils.creditRepaymentInfoTestBuilder()
                                                               .totalPaid(500.0)
                                                               .build();
        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .status(IN_REPAYMENT)
                                               .creditRepaymentInfo(Option.ofObj(repaymentInfo))
                                               .build();

        InRepaymentCardViewEntity viewModel = mapper.apply(draft);

        Mockito.verify(currencyUtils).formatAmount(500.0);
        assertThat(viewModel.formattedTotalRepaid()).isEqualTo("500,00");
    }

    @Test
    public void mapRepaymentInfoNextPayment() throws Exception {
        new ArrangeBuilder().withStringFromProvider("something")
                            .withSubstitutionStringFromProvider("next payment")
                            .withFormattedDate("Some date")
                            .withFormattedCurrency("500,00");
        CreditRepaymentInfo repaymentInfo = CreditDataTestUtils.creditRepaymentInfoTestBuilder()
                                                               .nextPaymentDate("2017-08-07T17:53:55.07+02:00")
                                                               .nextPayment(500.0)
                                                               .build();
        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .status(IN_REPAYMENT)
                                               .creditRepaymentInfo(Option.ofObj(repaymentInfo))
                                               .build();

        InRepaymentCardViewEntity viewModel = mapper.apply(draft);

        Mockito.verify(timeUtils).formatDateString("2017-08-07T17:53:55.07+02:00", DASHBOARD_DATE_FORMAT_PAID_OUT);
        Mockito.verify(currencyUtils).formatAmount(500.0);
        Mockito.verify(stringProvider).getStringAndApplySubstitutions(
                R.string.credit_dashboard_repayment_text,
                Pair.create("amount", "500,00"),
                Pair.create("date", "Some date"));
        assertThat(viewModel.formattedNextPayment()).isEqualTo("next payment");
    }

    @Test
    public void mapRepaymentInfoNextPaymentLabelIsFirstPaymentWhenTotalRepaidIsZero() throws Exception {
        new ArrangeBuilder().withStringFromProvider("First payment")
                            .withSubstitutionStringFromProvider("other something")
                            .withFormattedDate("Some date")
                            .withFormattedCurrency("500,00");
        CreditRepaymentInfo repaymentInfo = CreditDataTestUtils.creditRepaymentInfoTestBuilder()
                                                               .totalPaid(0d)
                                                               .build();
        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .status(IN_REPAYMENT)
                                               .creditRepaymentInfo(Option.ofObj(repaymentInfo))
                                               .build();

        InRepaymentCardViewEntity viewModel = mapper.apply(draft);

        Mockito.verify(stringProvider).getString(R.string.credit_dashboard_repayment_payment_first);
        assertThat(viewModel.nextPaymentLabel()).isEqualTo("First payment");
    }

    @Test
    public void mapRepaymentInfoNextPaymentLabelIsNextPaymentWhenTotalRepaidIsNotZero() throws Exception {
        new ArrangeBuilder().withStringFromProvider("Next payment")
                            .withSubstitutionStringFromProvider("other something")
                            .withFormattedDate("Some date")
                            .withFormattedCurrency("500,00");
        CreditRepaymentInfo repaymentInfo = CreditDataTestUtils.creditRepaymentInfoTestBuilder()
                                                               .totalPaid(100d)
                                                               .build();
        CreditDraft draft = CreditDataTestUtils.creditDraftTestBuilder()
                                               .status(IN_REPAYMENT)
                                               .creditRepaymentInfo(Option.ofObj(repaymentInfo))
                                               .build();

        InRepaymentCardViewEntity viewModel = mapper.apply(draft);

        Mockito.verify(stringProvider).getString(R.string.credit_dashboard_repayment_payment_next);
        assertThat(viewModel.nextPaymentLabel()).isEqualTo("Next payment");
    }

    @Test
    public void initialisedStateThrowsIllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid draft status for in repayment card:");

        CreditDraft testDraft = CreditDataTestUtils.creditDraftTestBuilder().status(INITIALISED).build();

        mapper.apply(testDraft);
    }

    @Test
    public void pendingProviderApprovalStateThrowsIllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid draft status for in repayment card:");

        CreditDraft testDraft = CreditDataTestUtils.creditDraftTestBuilder().status(PENDING_PROVIDER_APPROVAL).build();

        mapper.apply(testDraft);
    }

    @Test
    public void contractProcessingStateThrowsIllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid draft status for in repayment card:");

        CreditDraft testDraft = CreditDataTestUtils.creditDraftTestBuilder().status(CONTRACT_PROCESSING).build();

        mapper.apply(testDraft);
    }

    @Test
    public void pendingEsignStateThrowsIllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid draft status for in repayment card:");

        CreditDraft testDraft = CreditDataTestUtils.creditDraftTestBuilder().status(PENDING_ESIGN).build();

        mapper.apply(testDraft);
    }

    @Test
    public void waitingForDisbursementStateThrowsIllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid draft status for in repayment card:");

        CreditDraft testDraft = CreditDataTestUtils.creditDraftTestBuilder().status(WAITING_FOR_DISBURSEMENT).build();

        mapper.apply(testDraft);
    }

    @Test
    public void unexpectedStateThrowsIllegalStateException() throws Exception {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid draft status for in repayment card:");

        CreditDraft testDraft = CreditDataTestUtils.creditDraftTestBuilder().status(UNEXPECTED).build();

        mapper.apply(testDraft);
    }

    private class ArrangeBuilder {

        private ArrangeBuilder withSubstitutionStringFromProvider(String string) {
            Mockito.when(stringProvider.getStringAndApplySubstitutions(anyInt(), ArgumentMatchers.<Pair>any())).thenReturn(string);
            return this;
        }

        private ArrangeBuilder withStringFromProvider(String string) {
            Mockito.when(stringProvider.getString(anyInt())).thenReturn(string);
            return this;
        }

        private ArrangeBuilder withFormattedDate(String formattedDate) {
            Mockito.when(timeUtils.formatDateString(Mockito.anyString(), Mockito.anyString())).thenReturn(formattedDate);
            return this;
        }

        private ArrangeBuilder withFormattedCurrency(String formattedCurrency) {
            Mockito.when(currencyUtils.formatAmount(Mockito.anyDouble())).thenReturn(formattedCurrency);
            return this;
        }
    }

}