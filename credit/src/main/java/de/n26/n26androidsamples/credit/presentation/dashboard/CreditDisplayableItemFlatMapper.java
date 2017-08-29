package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem;
import de.n26.n26androidsamples.credit.data.CreditDraft;
import io.reactivex.Flowable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

import static de.n26.n26androidsamples.base.presentation.recyclerview.DisplayableItem.toDisplayableItem;
import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;

class CreditDisplayableItemFlatMapper implements Function<List<CreditDraft>, SingleSource<List<DisplayableItem>>> {

    @NonNull
    private final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper;

    @Inject
    public CreditDisplayableItemFlatMapper(@NonNull final InRepaymentCardViewEntityMapper inRepaymentCardViewEntityMapper) {
        this.inRepaymentCardViewEntityMapper = inRepaymentCardViewEntityMapper;
    }

    @Override
    public SingleSource<List<DisplayableItem>> apply(final List<CreditDraft> creditDrafts) throws Exception {
        return Flowable.fromIterable(creditDrafts)
                       .map(inRepaymentCardViewEntityMapper)
                       .map(this::wrapInDisplayableItem)
                       .toList();
    }

    private DisplayableItem wrapInDisplayableItem(InRepaymentCardViewEntity viewEntity) {
        return toDisplayableItem(viewEntity, IN_REPAYMENT);
    }
}
