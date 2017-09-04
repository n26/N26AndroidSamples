package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.data.CreditDraft;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem.toDisplayableItem;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;

class CreditDisplayableItemMapper implements Function<List<CreditDraft>, List<DisplayableItem>> {

    @NonNull
    private final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper;

    @Inject
    CreditDisplayableItemMapper(@NonNull final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper) {
        this.inRepaymentCardViewEntityMapper = inRepaymentCardViewEntityMapper;
    }

    @Override
    public List<DisplayableItem> apply(final List<CreditDraft> creditDrafts) throws Exception {
        return Observable.fromIterable(creditDrafts)
                         .map(inRepaymentCardViewEntityMapper)
                         .map(this::wrapInDisplayableItem)
                         .toList()
                         .blockingGet();
    }

    private DisplayableItem wrapInDisplayableItem(InRepaymentCardViewEntity viewEntity) {
        return toDisplayableItem(viewEntity, IN_REPAYMENT);
    }
}
