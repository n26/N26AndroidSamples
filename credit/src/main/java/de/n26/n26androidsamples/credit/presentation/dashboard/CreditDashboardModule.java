package de.n26.n26androidsamples.credit.presentation.dashboard;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;
import de.n26.n26androidsamples.base.common.preconditions.AndroidPreconditions;
import de.n26.n26androidsamples.base.presentation.recyclerview.ItemComparator;
import de.n26.n26androidsamples.base.presentation.recyclerview.RecyclerViewAdapter;
import de.n26.n26androidsamples.base.presentation.recyclerview.ViewHolderBinder;
import de.n26.n26androidsamples.base.presentation.recyclerview.ViewHolderFactory;
import de.n26.n26androidsamples.credit.presentation.dashboard.InRepaymentCardViewHolder.InRepaymentCardHolderBinder;
import de.n26.n26androidsamples.credit.presentation.dashboard.InRepaymentCardViewHolder.InRepaymentCardHolderFactory;

import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;

@Module
public abstract class CreditDashboardModule {

    @Provides
    static RecyclerViewAdapter provideRecyclerAdapter(ItemComparator itemComparator,
                                               Map<Integer, ViewHolderFactory> factoryMap,
                                               Map<Integer, ViewHolderBinder> binderMap,
                                               AndroidPreconditions androidPreconditions) {
        return new RecyclerViewAdapter(itemComparator, factoryMap, binderMap, androidPreconditions);
    }

    @Provides
    static ItemComparator provideComparator() {
        return new CreditDashboardItemComparator();
    }

    @Binds
    @IntoMap
    @IntKey(IN_REPAYMENT)
    abstract ViewHolderFactory provideInRepaymentCardViewHolderFactory(InRepaymentCardHolderFactory factory);

    @Binds
    @IntoMap
    @IntKey(IN_REPAYMENT)
    abstract ViewHolderBinder provideInRepaymentCardViewHolderBinder(InRepaymentCardHolderBinder binder);
}
