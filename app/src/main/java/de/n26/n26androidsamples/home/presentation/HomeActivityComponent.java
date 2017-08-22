package de.n26.n26androidsamples.home.presentation;

import dagger.Subcomponent;
import de.n26.n26androidsamples.base.injection.modules.ActivityModule;
import de.n26.n26androidsamples.base.injection.scopes.ActivityScope;
import de.n26.n26androidsamples.credit.presentation.dashboard.CreditDashboardComponent.CreditDashboardComponentCreator;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface HomeActivityComponent extends CreditDashboardComponentCreator {

    void inject(HomeActivity homeActivity);
}
