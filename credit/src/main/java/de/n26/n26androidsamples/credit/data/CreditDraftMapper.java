package de.n26.n26androidsamples.credit.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.data.EssentialParamMissingException;
import de.n26.n26androidsamples.credit.data.CreditDataConstants.RawDraftStatus;
import de.n26.n26androidsamples.credit.data.CreditDraft.CreditDraftStatus;
import io.reactivex.functions.Function;
import polanski.option.Option;

class CreditDraftMapper implements Function<CreditDraftRaw, CreditDraft> {

    private static final String TAG = CreditDraftMapper.class.getSimpleName();

    @Inject
    CreditDraftMapper() {}

    @Override
    @SuppressWarnings("ConstantConditions")
    public CreditDraft apply(@NonNull final CreditDraftRaw raw) throws Exception {
        assertEssentialParams(raw);

        return CreditDraft.builder()
                          .id(raw.id())
                          .purpose(raw.purposeName())
                          .amount(raw.amount())
                          .status(toStatus(raw.status()))
                          .creditRepaymentInfo(mapRepaymentInfo(raw.repaymentInfo()))
                          .imageUrl(raw.imageUrl())
                          .purposeId(raw.purposeId())
                          .build();
    }

    @NonNull
    private static Option<CreditRepaymentInfo> mapRepaymentInfo(@Nullable final CreditRepaymentInfoRaw raw) {
        if (raw == null) {
            return Option.none();
        }

        return Option.ofObj(CreditRepaymentInfoMapper.processRaw(raw));
    }

    @NonNull
    private static CreditDraftStatus toStatus(@NonNull final String status) {
        switch (status) {
            case RawDraftStatus.CONTRACT_PROCESSING:
                return CreditDraftStatus.CONTRACT_PROCESSING;
            case RawDraftStatus.IN_REPAYMENT:
                return CreditDraftStatus.IN_REPAYMENT;
            case RawDraftStatus.INITIALISED:
                return CreditDraftStatus.INITIALISED;
            case RawDraftStatus.PENDING_ESIGN:
                return CreditDraftStatus.PENDING_ESIGN;
            case RawDraftStatus.PENDING_PROVIDER_APPROVAL:
                return CreditDraftStatus.PENDING_PROVIDER_APPROVAL;
            case RawDraftStatus.WAITING_FOR_DISBURSEMENT:
                return CreditDraftStatus.WAITING_FOR_DISBURSEMENT;
            case RawDraftStatus.ADDITIONAL_ACCOUNT_REQUIRED:
                return CreditDraftStatus.ADDITIONAL_ACCOUNT_REQUIRED;
            default:
                Log.e(TAG, "Unknown status coming from backend: " + status);
                return CreditDraftStatus.UNEXPECTED;
        }
    }

    private static void assertEssentialParams(@NonNull final CreditDraftRaw raw) {
        String missingParams = "";

        if (raw.status() == null || raw.status().isEmpty()) {
            missingParams += "status";
        }

        if (raw.id() == null || raw.id().isEmpty()) {
            missingParams += " id";
        }

        if (raw.purposeName() == null || raw.purposeName().isEmpty()) {
            missingParams += " purpose";
        }

        if (raw.imageUrl() == null || raw.imageUrl().isEmpty()) {
            missingParams += " imageUrl";
        }

        if (!missingParams.isEmpty()) {
            throw new EssentialParamMissingException(missingParams, raw);
        }
    }
}
