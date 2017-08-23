package de.n26.n26androidsamples.credit.presentation.dashboard;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;
import de.n26.n26androidsamples.base.injection.qualifiers.ForActivity;
import de.n26.n26androidsamples.base.presentation.recyclerview.ViewHolderBinder;
import de.n26.n26androidsamples.base.presentation.recyclerview.ViewHolderFactory;

import static de.n26.n26androidsamples.credit.presentation.dashboard.CreditPresentationConstants.DisplayableTypes.IN_REPAYMENT;

@Module
public final class CreditDashboardModule {

    @IntoMap
    @IntKey(IN_REPAYMENT)
    @Provides
    ViewHolderFactory provideInRepaymentCardViewHolderFactory(@ForActivity Context context) {
        return new InRepaymentCardViewHolder.InRepaymentCardHolderFactory(context);
    }

    @IntoMap
    @IntKey(IN_REPAYMENT)
    @Provides
    ViewHolderBinder provideInRepaymentCardViewHolderBinder() {
        return new InRepaymentCardViewHolder.InRepaymentCardHolderBinder();
    }
}
