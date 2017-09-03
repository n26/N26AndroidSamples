package de.n26.n26androidsamples.credit.presentation;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.n26.n26androidsamples.base.presentation.utils.ViewModelUtil;
import de.n26.n26androidsamples.credit.presentation.dashboard.CreditDashboardViewModel;

@Module
public final class CreditViewModelModule {

    @Singleton
    @Provides
    static ViewModelProvider.Factory provideViewModelProviderFactory(ViewModelUtil viewModelUtil, CreditDashboardViewModel viewModel) {
        return viewModelUtil.createFor(viewModel);
    }
}
