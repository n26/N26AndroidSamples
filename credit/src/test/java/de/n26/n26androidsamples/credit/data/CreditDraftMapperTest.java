package de.n26.n26androidsamples.credit.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.n26.n26androidsamples.base.data.EssentialParamMissingException;
import de.n26.n26androidsamples.credit.data.CreditDataConstants.RawDraftStatus;
import de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus;
import de.n26.n26androidsamples.credit.test_common.BaseTest;
import polanski.option.Option;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditDraftMapperTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CreditDraftMapper mapper;

    @Before
    public void setUp() {
        mapper = new CreditDraftMapper();
    }

    @Test
    public void essentialParamMissingExceptionIsThrownWhenTheMandatoryParamsAreMissing() throws Exception {
        thrown.expect(EssentialParamMissingException.class);
        thrown.expectMessage("status id purpose imageUrl");

        mapper.apply(CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(null)
                .id(null)
                .purposeName(null)
                .imageUrl(null)
                .build());
    }

    @Test
    public void amountIsMappedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .amount(10.0)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.amount()).isEqualTo(10.0);
    }

    @Test
    public void idIsMappedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .id("10")
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.id()).isEqualTo("10");
    }

    @Test
    public void purposeIsMappedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .purposeName("Electronics")
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.purpose()).isEqualTo("Electronics");
    }

    @Test
    public void purposeIdIsMappedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .purposeId(5)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.purposeId()).isEqualTo(5);
    }

    @Test
    public void imageUrlIsMappedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .imageUrl("imageUrl")
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.imageUrl()).isEqualTo("imageUrl");
    }

    @Test
    public void noRepaymentInfoIsMappedToNone() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .repaymentInfo(null)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.creditRepaymentInfo()).isEqualTo(Option.none());
    }

    @Test
    public void repaymentInfoIsMappedAndWrappedInOption() throws Exception {
        CreditRepaymentInfoRaw repaymentInfoRaw = CreditDataTestUtils.creditRepaymentInfoRawTestBuilder().build();
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .repaymentInfo(repaymentInfoRaw)
                .build();

        CreditDraft draft = mapper.apply(raw);

        CreditRepaymentInfo expected = CreditRepaymentInfoMapper.processRaw(repaymentInfoRaw);
        assertThat(draft.creditRepaymentInfo()).isEqualTo(Option.ofObj(expected));
    }

    @Test
    public void contractProcessingStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.CONTRACT_PROCESSING)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.CONTRACT_PROCESSING);
    }

    @Test
    public void inRepaymentStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.IN_REPAYMENT)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.IN_REPAYMENT);
    }

    @Test
    public void initialisedStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.INITIALISED)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.INITIALISED);
    }

    @Test
    public void pendingEsignStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.PENDING_ESIGN)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.PENDING_ESIGN);
    }

    @Test
    public void pendingProviderApprovalStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.PENDING_PROVIDER_APPROVAL)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.PENDING_PROVIDER_APPROVAL);
    }

    @Test
    public void waitingForDisbursementStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.WAITING_FOR_DISBURSEMENT)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.WAITING_FOR_DISBURSEMENT);
    }

    @Test
    public void additionalAccountRequiredStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status(RawDraftStatus.ADDITIONAL_ACCOUNT_REQUIRED)
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.ADDITIONAL_ACCOUNT_REQUIRED);
    }

    @Test
    public void unexpectedStateIsParsedCorrectly() throws Exception {
        CreditDraftRaw raw = CreditDataTestUtils.creditDraftRawTestBuilder()
                .status("UNEXPECTED")
                .build();

        CreditDraft draft = mapper.apply(raw);

        assertThat(draft.status()).isEqualTo(CreditDraftStatus.UNEXPECTED);
    }
}
