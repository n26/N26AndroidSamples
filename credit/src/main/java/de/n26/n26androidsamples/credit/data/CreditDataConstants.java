package de.n26.n26androidsamples.credit.data;

interface CreditDataConstants {

    interface RawDraftStatus {
        String INITIALISED = "INITIALISED";
        String PENDING_PROVIDER_APPROVAL = "PENDING_PROVIDER_APPROVAL";
        String PENDING_ESIGN = "PENDING_ESIGN";
        String CONTRACT_PROCESSING = "CONTRACT_PROCESSING";
        String WAITING_FOR_DISBURSEMENT = "WAITING_FOR_DISBURSEMENT";
        String IN_REPAYMENT = "IN_REPAYMENT";
        String ADDITIONAL_ACCOUNT_REQUIRED = "ADDITIONAL_ACCOUNT_REQUIRED";
    }
}
